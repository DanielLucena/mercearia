package com.imd.mercearia.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.service.FornecedorService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorController {
    @Autowired
    private FornecedorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fornecedor save(@RequestBody Fornecedor fornecedor) {
        return service.criarFornecedor(fornecedor);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {

        service.getFornecedorPorId(id).map(p -> {
            fornecedor.setId(p.getId());
            service.atualizarFornecedor(fornecedor);
            return fornecedor;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Fornecedor não encontrado."));
    }

    @GetMapping("{id}")
    public Fornecedor getById(@PathVariable Integer id) {

        return service
                .getFornecedorPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service
                .getFornecedorPorId(id).map(p -> {
                    service.deletarFornecedor(id);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado."));
    }

    @GetMapping
    public List<Fornecedor> find(Fornecedor filtro) {
        return service.listaFornecedorPorFiltro(filtro);
    }

}
