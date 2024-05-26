package com.imd.mercearia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imd.mercearia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByLogin(String login);
}
