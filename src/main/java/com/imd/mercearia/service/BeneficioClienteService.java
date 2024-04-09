package com.imd.mercearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.BeneficioCliente;
import com.imd.mercearia.repository.BeneficioClienteRepository;

@Component
public class BeneficioClienteService {
    @Autowired
    BeneficioClienteRepository beneficioClienteRepository;

    public void salvarBeneficioCliente(BeneficioCliente beneficioCliente) {
        beneficioClienteRepository.save(beneficioCliente);
    }

    public BeneficioCliente getBeneficioClienteByCpf(String cpf) {
        return beneficioClienteRepository.findById(cpf).orElse(null);
    }
}
