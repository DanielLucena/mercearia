package com.imd.mercearia.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.model.Cliente;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cliente")
@Tag(name = "Cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(summary = "Cria um cliente", method = "POST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente) {
        return service.salvarCliente(cliente);
    }

    @Operation(summary = "Atualiza um cliente", method = "PUT")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        service.buscarClientePorId(id)
                .map(p -> {
                    cliente.setId(p.getId());
                    service.atualizarCliente(cliente);
                    return cliente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."));
    }

    @Operation(summary = "Atualiza parcialmente um cliente", method = "PATCH")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Cliente cliente) {
        service.buscarClientePorId(id)
                .map(p -> {
                    if (cliente.getNome() != null) {
                        p.setNome(cliente.getNome());
                    }
                    if (cliente.getCpf() != null) {
                        p.setCpf(cliente.getCpf());
                    }
                    service.atualizarCliente(p);
                    return p;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."));
    }

    @Operation(summary = "Recupera um cliente pelo ID", method = "GET")
    @GetMapping("{id}")
    public Cliente getByIdCliente(@PathVariable Integer id) {
        return service
                .buscarClientePorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."));
    }

    @Operation(summary = "Exclui um cliente", method = "DELETE")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service
                .buscarClientePorId(id)
                .map(p -> {
                    service.deletarCliente(id);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."));
    }

    @Operation(summary = "Recupera uma listagem de todos os clientes", method = "GET")
    @GetMapping
    public List<Cliente> find(Cliente filtro) {
        return service.listaClientesPorfiltro(filtro);
    }


    @Operation(summary = "Recupera uma listagem de pedidos feito por um cliente", method = "GET")
    @GetMapping("/pedidos/{id}")
    public List<Pedido> getPedidosFromCliente(@PathVariable Integer id) {
        return service.buscaPedidosByClienteId(id);
    }
}
