package com.imd.mercearia.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imd.mercearia.dto.ProdutoCreationDTO;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.service.ProdutoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}
