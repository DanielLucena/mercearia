package com.imd.mercearia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.exception.RegistroNaoEncontradoException;
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

    public void atualizarFornecedor(Fornecedor fornecedor, Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do fornecedor não pode ser nulo.");
        }
        fornecedorRepository.findById(id)
                .map(p -> {
                    fornecedor.setId(p.getId());
                    fornecedorRepository.save(fornecedor);
                    return fornecedor;
                }).orElseThrow(() -> new RegistroNaoEncontradoException(Fornecedor.class, id));
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
        Example<Fornecedor> example = Example.of(filtro, matcher);
        return fornecedorRepository.findAll(example, Sort.by(Sort.Direction.ASC, "id"));
    }

}
