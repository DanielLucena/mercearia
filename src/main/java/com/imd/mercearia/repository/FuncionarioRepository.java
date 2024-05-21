package com.imd.mercearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imd.mercearia.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
