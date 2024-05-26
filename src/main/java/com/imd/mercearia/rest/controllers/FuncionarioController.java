package com.imd.mercearia.rest.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.imd.mercearia.model.Funcionario;
import com.imd.mercearia.service.FuncionarioService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario save(@RequestBody Funcionario funcionario) {
        return service.salvarFuncionario(funcionario);
    }

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

    @GetMapping("{id}")
    public Funcionario getByIdFuncionario(@PathVariable Integer id) {
        return service.buscarFuncionarioPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Funcionário não encontrado."));
    }

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

    @GetMapping
    public List<Funcionario> find(Funcionario filtro) {
        return service.listaFuncionariosPorfiltro(filtro);
    }
}
