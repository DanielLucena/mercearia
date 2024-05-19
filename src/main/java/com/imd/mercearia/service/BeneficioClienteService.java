package com.imd.mercearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.BeneficioCliente;
import com.imd.mercearia.repository.BeneficioClienteRepository;

import jakarta.transaction.Transactional;

@Component
public class BeneficioClienteService {
    @Autowired
    BeneficioClienteRepository beneficioClienteRepository;

    public BeneficioCliente salvarBeneficioCliente(BeneficioCliente beneficioCliente) {
        BeneficioCliente novoBeneficioCliente = beneficioClienteRepository.save(beneficioCliente);
        return novoBeneficioCliente;
    }

    public BeneficioCliente obterOuCriarPorCPF(String cpf) {
        BeneficioCliente beneficioCliente = beneficioClienteRepository.findByCpf(cpf);
        if (beneficioCliente == null) {
            beneficioCliente = new BeneficioCliente();
            beneficioCliente.setCpf(cpf);
            beneficioCliente = beneficioClienteRepository.save(beneficioCliente);
        }

        return beneficioCliente;
    }

    @Transactional
    public double consomePontosCashbackCliente(BeneficioCliente beneficioCliente) {
        double cashback = beneficioCliente.getPontosCashback();
        beneficioCliente.setPontosCashback(0.);
        salvarBeneficioCliente(beneficioCliente);
        return cashback;
    }

    @Transactional
    public void incrementaPontosCashbackCliente(BeneficioCliente beneficioCliente, double pontos) {
        double pontosInicial = beneficioCliente.getPontosCashback();
        beneficioCliente.setPontosCashback(pontosInicial + pontos);
        salvarBeneficioCliente(beneficioCliente);
    }

    public void update(BeneficioCliente beneficioCliente) {
        beneficioClienteRepository.save(beneficioCliente);
    }

    public boolean existsBeneficioClienteComCpf(String cpf) {
        return beneficioClienteRepository.findByCpf(cpf) != null;
    }

    public void deletarBeneficioCliente(BeneficioCliente beneficioCliente) {
        beneficioClienteRepository.delete(beneficioCliente);
    }

}
