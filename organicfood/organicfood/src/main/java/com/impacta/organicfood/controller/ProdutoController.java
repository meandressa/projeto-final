package com.impacta.organicfood.controller;


import com.impacta.organicfood.model.Categoria;
import com.impacta.organicfood.model.Produto;
import com.impacta.organicfood.repository.CategoriaRepository;
import com.impacta.organicfood.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Cacheable(value = "todosProdutos")
	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@Cacheable(value = "produtoPorId", key = "#id")
	@GetMapping("/id/{id}")
	public Produto getById(@PathVariable long id) {
		Optional<Produto> produtoOptional = repository.findById(id);
		return produtoOptional.orElse(null);
	}
	@Cacheable(value = "produtosPorNome", key = "#nome")
	@GetMapping("/nome/{nome}")
	public List<Produto> getByNome(@PathVariable String nome) {
		return repository.findAllByNomeContainingIgnoreCase(nome);
	}


	@PostMapping
	public ResponseEntity<Produto> post(@RequestBody Produto produto) {
		Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
		produto.setCategoria(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> put(@PathVariable long id, @RequestBody Produto produtoAtualizado) {
		Optional<Produto> produtoExistente = repository.findById(id);

		if (produtoExistente.isPresent()) {
			Produto produto = produtoExistente.get();

			produto.setNome(produtoAtualizado.getNome());
			produto.setDescricao(produtoAtualizado.getDescricao());
			produto.setValor(produtoAtualizado.getValor());
			produto.setEstoque(produtoAtualizado.getEstoque());
			produto.setImagem(produtoAtualizado.getImagem());
			produto.setCategoria(produtoAtualizado.getCategoria());

			return ResponseEntity.ok(repository.save(produto));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
	
}
