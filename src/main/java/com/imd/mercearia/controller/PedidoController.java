package com.imd.mercearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.service.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/getListaPedidos")
    public String getListaPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.getListaPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedido/listaPedido";
    }
}
