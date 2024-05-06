package com.imd.mercearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imd.mercearia.model.BeneficioCliente;

public interface BeneficioClienteRepository extends JpaRepository<BeneficioCliente, Integer> {
    // @Query(value = "select b from beneficio_produto where b.cpf like %:cpf%")
    public BeneficioCliente findByCpf(String cpf);
}
