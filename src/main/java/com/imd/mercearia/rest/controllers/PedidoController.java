package com.imd.mercearia.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.rest.dto.PedidoCreationDto;
import com.imd.mercearia.service.PedidoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    PedidoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido save(@RequestBody PedidoCreationDto dto) {
        System.out.println("PedidoController.save() " + dto);
        return service.processarPedido(dto);
    }

    @GetMapping
    public List<Pedido> find(PedidoCreationDto dto) {
        return service.listaPedidosPorFiltro(dto);
    }

}
