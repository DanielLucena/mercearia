package com.imd.mercearia.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.exception.RegistroNaoEncontradoException;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.rest.dto.ProdutoCreationDTO;
import com.imd.mercearia.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

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

    @Operation(summary = "Recupera um produto pelo ID", method = "GET")   
    @GetMapping("{id}")
    public Produto getById(@PathVariable Integer id) {
        try {
            return service.getProdutoById(id);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.");
        }

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
