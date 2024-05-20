package com.imd.mercearia.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imd.mercearia.model.Produto;
import com.imd.mercearia.rest.dto.ProdutoCreationDTO;
import com.imd.mercearia.service.ProdutoService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
    @Autowired
    ProdutoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody ProdutoCreationDTO dto) {
        System.out.println("controller");
        Produto produto = service.criarProduto(dto);
        return produto.getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody ProdutoCreationDTO dto) {
        Produto produto = service.converteDtoParaProduto(dto);

        service.atualizarProduto(produto, id);
    }

    @GetMapping("{id}")
    public Produto getById(@PathVariable Integer id) {
        return service.getProdutoById(id);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteProdutoById(id);
    }

    @GetMapping
    public List<Produto> find() {
        return service.getListaProdutos();
    }

}
