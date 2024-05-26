package com.imd.mercearia.rest.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.imd.mercearia.model.Funcionario;
import com.imd.mercearia.service.FuncionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/funcionario")
@Tag(name = "Funcionário")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @Operation(summary = "Cria um funcionário", method = "POST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario save(@RequestBody Funcionario funcionario) {
        return service.salvarFuncionario(funcionario);
    }

    @Operation(summary = "Edita um funcionário", method = "PUT")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
        service.buscarFuncionarioPorId(id)
                .map(f -> {
                    funcionario.setId(f.getId());
                    service.atualizarFuncionario(funcionario);
                    return funcionario;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Funcionário não encontrado."));
    }

    @Operation(summary = "Recupera um funcionário pelo ID", method = "GET")
    @GetMapping("{id}")
    public Funcionario getByIdFuncionario(@PathVariable Integer id) {
        return service.buscarFuncionarioPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Funcionário não encontrado."));
    }

    @Operation(summary = "Exclui um funcionário", method = "DELETE")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.buscarFuncionarioPorId(id)
                .map(f -> {
                    service.deletarFuncionario(id);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Funcionário não encontrado."));
    }

    @Operation(summary = "Retorna uma lista de funcionários", method = "GET")
    @GetMapping
    public List<Funcionario> find(Funcionario filtro) {
        return service.listaFuncionariosPorfiltro(filtro);
    }
}
