//package com.impacta.organicfood.service;
//
//
//import com.impacta.organicfood.model.Usuario;
//import com.impacta.organicfood.model.UsuarioLogin;
//import com.impacta.organicfood.repository.UsuarioRepository;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.Charset;
//import java.util.Optional;
//
//@Service
//public final class UsuarioService {
//	@Autowired
//	private UsuarioRepository repository;
//
//	public Usuario CadastrarUsuario(Usuario usuario) {
//		if(repository.findAllByEmailContainingIgnoreCase(usuario.getEmail()).isPresent()) {
//			return null;
//		}
//
////		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//		String senhaEncoder = encoder.encode(usuario.getSenha());
//		usuario.setSenha(senhaEncoder);
//
//		return repository.save(usuario);
//	}
//
//	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> user){
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		Optional<Usuario> usuario = repository.findAllByEmailContainingIgnoreCase(user.get().getEmail());
//
//		if(usuario.isPresent()) {
//			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())){
//
//			String auth = user.get().getEmail()+ ":" + user.get().getSenha();
//			byte[] encondedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
//			String authHeader = "Basic "+ new String(encondedAuth);
//			user.get().setToken(authHeader);
//			user.get().setNome(usuario.get().getNome());
//
//			user.get().setSenha(usuario.get().getSenha());
//			user.get().setTipoUsuario(usuario.get().getTipoUsuario());
//
//			return user;
//
//			}
//		}
//
//		return null;
//	}
//}