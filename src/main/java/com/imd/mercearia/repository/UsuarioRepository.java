package com.imd.mercearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imd.mercearia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
