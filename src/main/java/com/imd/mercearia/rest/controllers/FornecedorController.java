package com.imd.mercearia.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/fornecedor")
@Tag(name = "Fornecedor")
@CrossOrigin(origins = "*")
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
        service.atualizarFornecedor(fornecedor, id);
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
