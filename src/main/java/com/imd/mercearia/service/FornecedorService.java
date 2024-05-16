package com.imd.mercearia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.repository.FornecedorRepository;
import com.imd.mercearia.repository.ProdutoRepository;

@Component
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    public List<Fornecedor> getListaFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor criarFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> getFornecedorPorId(Integer id) {
        return fornecedorRepository.findById(id);
    }

    public void atualizarFornecedor(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

    public void deletarFornecedor(Integer id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);

        produtoRepository.deleteByFornecedor(fornecedor);
        fornecedorRepository.delete(fornecedor);
    }

    public List<Produto> getProdutosPorFornecedor(Fornecedor fornecedor) {
        return produtoRepository.findByFornecedor(fornecedor);
    }

    public List<Fornecedor> listaFornecedorPorFiltro(Fornecedor filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return fornecedorRepository.findAll(example);
    }

}
