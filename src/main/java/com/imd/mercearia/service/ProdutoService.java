package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.exception.RegraNegocioException;
import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.repository.FornecedorRepository;
import com.imd.mercearia.repository.ProdutoRepository;
import com.imd.mercearia.rest.dto.ProdutoCreationDTO;

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
        Produto produto = converteDtoParaProduto(dto);
        return produtoRepository.save(produto);
    }

    public Produto converteDtoParaProduto(ProdutoCreationDTO dto) {
        Integer idFornecedor = dto.getFornecedor();
        Fornecedor fornecedor = fornecedorRepository
                .findById(idFornecedor)
                .orElseThrow(() -> new RegraNegocioException("Código de fornecedor inválido."));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidade());
        produto.setFornecedor(fornecedor);
        return produto;
    }

    public Produto getProdutoById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado."));

    }

    public void atualizarProduto(Produto produto, Integer id) {
        produtoRepository.findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    produtoRepository.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado."));
    }

    public void deleteProduto(Produto produto) {
        produtoRepository.delete(produto);
    }

    public void deleteProdutoById(Integer id) {
        produtoRepository.findById(id)
                .map(p -> {
                    produtoRepository.delete(p);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado."));

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

    // nao esta em uso ainda
    public List<Produto> listaProdutoPorFiltro(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);
    }

    public void validaEstoqueProdutoSuficiente(Integer idProduto, int quantidade) {
        produtoRepository.findById(idProduto)
                .map(p -> {
                    if (quantidade > p.getQuantidadeEstoque()) {
                        throw new EstoqueInsuficienteException(p, quantidade);
                    }
                    return Void.TYPE;
                })
                .orElseThrow(() -> new RegraNegocioException("Código de produto inválido. id: " + idProduto));

    }
}
