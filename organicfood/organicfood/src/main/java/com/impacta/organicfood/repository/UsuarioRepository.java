package com.impacta.organicfood.repository;


import com.impacta.organicfood.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
	public Optional<Usuario> findAllByEmailContainingIgnoreCase (String email);
}
