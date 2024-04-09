package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.Fornecedor;
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

    public List<Produto> getProdutosPorFornecedor(Fornecedor fornecedor) {
        return produtoRepository.findByFornecedor(fornecedor);
    }

    public List<Produto> getListaProdutosOrderByName() {
        return produtoRepository.getProdutosOrded();
    }

    public void adicionarAoEstoque(Produto produto, int quantidade) {
        if (quantidade > 0) {
            int novaQuantidade = produto.getQuantidadeEstoque() + quantidade;
            produto.setQuantidadeEstoque(novaQuantidade);
            produtoRepository.save(produto);
        }
    }

    public void darBaixaAoEstoque(Produto produto, int quantidade) {
        int novaQuantidade;
        if (quantidade < produto.getQuantidadeEstoque()) {
            novaQuantidade = produto.getQuantidadeEstoque() - quantidade;

        } else {
            novaQuantidade = 0;
        }
        produto.setQuantidadeEstoque(novaQuantidade);
        produtoRepository.save(produto);
    }
}
