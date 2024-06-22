package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.exception.RegraNegocioException;
import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.exception.RegistroNaoEncontradoException;
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
        Integer idFornecedor = dto.getFornecedorId();
        if (idFornecedor == null) {
            throw new RegraNegocioException("Código de fornecedor não pode ser nulo.");
        }
        Fornecedor fornecedor = fornecedorRepository
                .findById(idFornecedor)
                .orElseThrow(() -> new RegraNegocioException("Código de fornecedor inválido."));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        produto.setFornecedor(fornecedor);
        return produto;
    }

    public Produto getProdutoById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto não pode ser nulo.");
        }
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(Produto.class, id));
    }

    public void atualizarProduto(Produto produto, Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto não pode ser nulo.");
        }
        produtoRepository.findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    produtoRepository.save(produto);
                    return produto;
                }).orElseThrow(() -> new RegistroNaoEncontradoException(Produto.class, id));
    }

    public void deleteProduto(Produto produto) {
        if (produto == null || produto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto ou ID do produto não pode ser nulo.");
        }
        produtoRepository.delete(produto);
    }

    public void deleteProdutoById(Integer id) {
        if (id == null) {
            throw new RegistroNaoEncontradoException(Produto.class, id);
        }
        produtoRepository.findById(id)
                .map(p -> {
                    produtoRepository.delete(p);
                    return Void.TYPE;
                }).orElseThrow(() -> new RegistroNaoEncontradoException(Produto.class, id));
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
        int novaQuantidade = Math.max(produto.getQuantidadeEstoque() - quantidade, 0);
        produto.setQuantidadeEstoque(novaQuantidade);
        produtoRepository.save(produto);
    }

    public void validaEstoqueProdutoSuficiente(Integer idProduto, int quantidade) {
        produtoRepository.findById(idProduto)
                .map(p -> {
                    if (quantidade > p.getQuantidadeEstoque()) {
                        throw new EstoqueInsuficienteException(p, quantidade);
                    }
                    return Void.TYPE;
                })
                .orElseThrow(() -> new RegistroNaoEncontradoException(Produto.class, idProduto));
    }

    public List<Produto> listaProdutosPorFiltro(ProdutoCreationDTO dto) {
        Produto filtro = new Produto(dto.getNome(), dto.getPreco(), dto.getQuantidadeEstoque());
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("preco", "quantidadeEstoque")
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example, Sort.by(Sort.Direction.ASC, "id"));
    }
}
