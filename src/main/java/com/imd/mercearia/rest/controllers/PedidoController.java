package com.imd.mercearia.rest.controllers;

import org.springframework.web.bind.annotation.*;

import com.imd.mercearia.model.Pedido;

import com.imd.mercearia.rest.dto.PedidoCreationDTO;
import com.imd.mercearia.rest.dto.InformacoesPedidoDto;

import com.imd.mercearia.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pedido")
@Tag(name = "Pedido")
public class PedidoController {

    @Autowired
    PedidoService service;

    @Operation(summary = "Salva um novo pedido", method = "POST")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido save(@RequestBody PedidoCreationDTO dto) {
        System.out.println("PedidoController.save() " + dto);
        return service.processarPedido(dto);
    }

    @Operation(summary = "Atualiza um pedido", method = "PUT")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Pedido pedido) {
        service.getPedidoById(id).map(p -> {
            pedido.setId(p.getId());
            pedido.setCpfCliente(p.getCpfCliente());
            pedido.setValorTotal(p.getValorTotal());

            service.atualizarPedido(pedido);
            return pedido;

        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Pedido não encontrado."));
    }

    @Operation(summary = "Atualiza parcialmente um pedido", method = "PATCH")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Pedido pedido) {
        Pedido pedidoExistente = service.getPedidoById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (pedido.getCpfCliente() != null) {
            pedidoExistente.setCpfCliente(pedido.getCpfCliente());
        }
        if (pedido.getValorTotal() != null) {
            pedidoExistente.setValorTotal(pedido.getValorTotal());
        }

        service.atualizarPedido(pedidoExistente);
    }

    @Operation(summary = "Busca pedidos com filtro", method = "GET")
    @GetMapping
    public List<Pedido> find(PedidoCreationDTO dto) {
        return service.listaPedidosPorFiltro(dto);
    }

    @Operation(summary = "Busca pedidos por id", method = "GET")
    @GetMapping(value = "{id}")
    public InformacoesPedidoDto getById(@PathVariable Integer id) {
        return service.converterPedidoParaDto(service.getPedidoById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Pedido não encontrado.")));
    }

    @Operation(summary = "Deleta um pedido", method = "DELETE")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deletePedido(id);
    }

}
