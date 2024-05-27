package com.imd.mercearia.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.service.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/fornecedor")
@Tag(name = "Fornecedor")
public class FornecedorController {
    @Autowired
    private FornecedorService service;

    @Operation(summary = "Cria um fornecedor", method = "POST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fornecedor save(@RequestBody Fornecedor fornecedor) {
        return service.criarFornecedor(fornecedor);
    }

    @Operation(summary = "Atualiza um fornecedor", method = "PUT")
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

    @Operation(summary = "Atualiza parcialmente um fornecedor", method = "PATCH")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
        Fornecedor fornecedorExistente = service.getFornecedorPorId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));

        if (fornecedor.getNome() != null) {
            fornecedorExistente.setNome(fornecedor.getNome());
        }

        service.atualizarFornecedor(fornecedorExistente);
    }

    @Operation(summary = "Recupera um fornecedor pelo ID", method = "GET")
    @GetMapping("{id}")
    public Fornecedor getById(@PathVariable Integer id) {

        return service
                .getFornecedorPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado."));
    }

    @Operation(summary = "Exclui um fornecedor", method = "DELETE")
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

    @Operation(summary = "Retorna uma listagem de todos os fornecedores", method = "GET")
    @GetMapping
    public List<Fornecedor> find(Fornecedor filtro) {
        return service.listaFornecedorPorFiltro(filtro);
    }

}
