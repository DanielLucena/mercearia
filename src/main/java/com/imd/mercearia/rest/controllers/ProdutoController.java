package com.imd.mercearia.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.exception.RegistroNaoEncontradoException;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.rest.dto.ProdutoCreationDTO;
import com.imd.mercearia.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Produto")
public class ProdutoController {
    @Autowired
    ProdutoService service;

    @Operation(summary = "Cria um produto", method = "POST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody ProdutoCreationDTO dto) {
        System.out.println("controller");
        Produto produto = service.criarProduto(dto);
        return produto.getId();
    }

    @Operation(summary = "Edita um produto", method = "PUT")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody ProdutoCreationDTO dto) {
        Produto produto = service.converteDtoParaProduto(dto);

        service.atualizarProduto(produto, id);
    }

    @Operation(summary = "Atualiza parcialmente um produto", method = "PATCH")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody ProdutoCreationDTO dto) {
        Produto produto = service.getProdutoById(id);
        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }

        if (dto.getNome() != null) {
            produto.setNome(dto.getNome());
        }
        if (dto.getPreco() != null) {
            produto.setPreco(dto.getPreco());
        }

        service.atualizarProduto(produto, id);
    }

    @Operation(summary = "Recupera um produto pelo ID", method = "GET")   
    @GetMapping("{id}")
    public Produto getById(@PathVariable Integer id) {
        return service.getProdutoById(id);
    }

    @Operation(summary = "Exclui um produto", method = "DELETE")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteProdutoById(id);
    }

    @Operation(summary = "Mostra a listagem de todos os produtos", method = "POST")
    @GetMapping
    public List<Produto> find() {
        return service.getListaProdutos();
    }

}
