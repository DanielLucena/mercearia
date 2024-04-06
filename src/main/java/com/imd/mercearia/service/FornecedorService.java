package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void criarFornecedor(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

    public Fornecedor getFornecedorPorId(Integer id) {
        return fornecedorRepository.findById(id).orElse(null);
    }

    public void atualizarFornecedor(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

    public void deletarFornecedor(Integer id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);
        List<Produto> produtos = produtoRepository.findByFornecedor(fornecedor);
        if (!produtos.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir o fornecedor pois há produtos associados a ele. Exclua os produtos primeiro.");
        }
        fornecedorRepository.deleteById(id);
        
    }

    public List<Produto> getProdutosPorFornecedor(Fornecedor fornecedor) {
        return produtoRepository.findByFornecedor(fornecedor);
    }
    
}
