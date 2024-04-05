package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.Produto;
import com.imd.mercearia.repository.ProdutoRepository;

@Component
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public List<Produto> getListaProdutos() {
        return produtoRepository.findAll();
    }

    public void criarProduto(Produto produto) {
        produtoRepository.save(produto);
    }

    public Produto getProdutoById(Integer id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public void atualizarProduto(Produto produto) {
        produtoRepository.save(produto);
    }

    public void deleteProduto(Produto produto) {
        produtoRepository.delete(produto);
    }

    public void deleteProdutoById(Integer id) {
        deleteProduto(getProdutoById(id));
    }
}
