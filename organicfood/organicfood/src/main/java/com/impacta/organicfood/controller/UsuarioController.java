package com.impacta.organicfood.controller;


import com.impacta.organicfood.model.Usuario;
import com.impacta.organicfood.model.UsuarioLogin;
import com.impacta.organicfood.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders ="*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin>Autentication(@RequestBody Optional <UsuarioLogin> user){
        return usuarioService.logar(user).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario){
        Optional<Usuario> user = Optional.ofNullable(usuarioService.cadastrarUsuario(usuario));
        try {
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}