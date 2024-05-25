package com.imd.mercearia.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.rest.dto.PedidoCreationDTO;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.service.ProdutoPedidoService;
import com.imd.mercearia.service.BeneficioClienteService;
import com.imd.mercearia.service.PedidoService;
import com.imd.mercearia.service.ProdutoService;

@Controller
@RequestMapping("/pedido")
public class OPedidoController {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoPedidoService produtoPedidoService;

    @Autowired
    BeneficioClienteService beneficioClienteService;

    @RequestMapping("/getListaPedidos")
    public String getListaPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.getListaPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedido/listaPedido";
    }

    // @RequestMapping("/showForm")
    // public String showFormPedido(Model model) {
    // PedidoCreationDto produtosForm = new PedidoCreationDto(new
    // ArrayList<ProdutoPedido>());
    // produtosForm.setUsandoCashback(true);
    // List<Produto> produtos = produtoService.getListaProdutosOrderByName();
    // for (Produto produto : produtos) {
    // ProdutoPedido item = new ProdutoPedido();
    // item.setProduto(produto);
    // item.setQuantidade(0);
    // produtosForm.addItem(item);
    // }

    // model.addAttribute("form", produtosForm);
    // return "pedido/formPedido";
    // }

    // @RequestMapping("/addPedido")
    // public String showFormPedido(@ModelAttribute PedidoCreationDto form, Model
    // model) {
    // try {
    // Pedido pedido = pedidoService.processarPedido(form);
    // model.addAttribute("pedido", pedido);
    // return "pedido/detalhesPedido";
    // } catch (EstoqueInsuficienteException e) {
    // model.addAttribute("mensagem", e.getMessage());
    // return "pedido/pedidoInvalido";
    // }

    // }

}
