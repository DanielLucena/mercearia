package com.imd.mercearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.mercearia.model.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByCpf(String cpf);
}
