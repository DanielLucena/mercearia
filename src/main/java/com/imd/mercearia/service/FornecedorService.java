package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.repository.FornecedorRepository;

@Component
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    public List<Fornecedor> getListaFornecedores() {
        return fornecedorRepository.findAll();
    }
}
