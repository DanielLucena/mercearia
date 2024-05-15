package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.dto.ProdutoCreationDTO;
import com.imd.mercearia.exception.RegraNegocioException;
import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.repository.FornecedorRepository;
import com.imd.mercearia.repository.ProdutoRepository;

@Component
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    public List<Produto> getListaProdutos() {
        return produtoRepository.findAll();
    }

    public Produto criarProduto(ProdutoCreationDTO dto) {
        Integer idFornecedor = dto.getFornecedor();
        Fornecedor fornecedor = fornecedorRepository
                .findById(idFornecedor)
                .orElseThrow(() -> new RegraNegocioException("Código de fornecedor inválido."));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidade());
        produto.setFornecedor(fornecedor);
        return produtoRepository.save(produto);
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
