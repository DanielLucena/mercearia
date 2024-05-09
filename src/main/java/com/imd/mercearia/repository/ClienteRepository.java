package com.imd.mercearia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imd.mercearia.model.Cliente;
import com.imd.mercearia.model.Pedido;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Cliente findByCpf(String cpf);

}
