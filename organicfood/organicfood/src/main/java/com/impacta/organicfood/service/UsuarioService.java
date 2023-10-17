package com.impacta.organicfood.service;

import com.impacta.organicfood.model.Usuario;
import com.impacta.organicfood.model.UsuarioLogin;
import com.impacta.organicfood.repository.UsuarioRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public final class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (repository.findAllByEmailContainingIgnoreCase(usuario.getEmail()).isPresent()) {
            return null;
        }

        Base64 encoder = new Base64();

        // alterar para spring security
        String senhaBase64 = new String(encoder.encode(usuario.getSenha().getBytes(Charset.forName("US-ASCII"))));
        usuario.setSenha(senhaBase64);

        return repository.save(usuario);
    }

    public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> user) {
        Base64 encoder = new Base64();
        Optional<Usuario> usuario = repository.findAllByEmailContainingIgnoreCase(user.get().getEmail());

        if (usuario.isPresent()) {
            // Decodificar a senha do usuário
            String senhaUsuario = new String(encoder.decode(usuario.get().getSenha().getBytes(Charset.forName("US-ASCII"))));

            if (user.get().getSenha().equals(senhaUsuario)) {
                String auth = user.get().getEmail() + ":" + user.get().getSenha();
                byte[] encodedAuth = encoder.encode(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                user.get().setToken(authHeader);
                user.get().setNome(usuario.get().getNome());
                user.get().setSenha(usuario.get().getSenha());
                user.get().setTipoUsuario(usuario.get().getTipoUsuario());
                return user;
            }
        }

        return Optional.empty();
    }
}
