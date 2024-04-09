package com.imd.mercearia.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imd.mercearia.dto.ProdutoPedidoCreationDto;
import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.service.ProdutoPedidoService;
import com.imd.mercearia.service.PedidoService;
import com.imd.mercearia.service.ProdutoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoPedidoService produtoPedidoService;

    @RequestMapping("/getListaPedidos")
    public String getListaPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.getListaPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedido/listaPedido";
    }

    @RequestMapping("/showForm")
    public String showFormPedido(Model model) {
        ProdutoPedidoCreationDto produtosForm = new ProdutoPedidoCreationDto(new ArrayList<ProdutoPedido>());
        List<Produto> produtos = produtoService.getListaProdutosOrderByName();
        for (Produto produto : produtos) {
            ProdutoPedido item = new ProdutoPedido();
            item.setProduto(produto);
            item.setQuantidade(0);
            produtosForm.addItem(item);
        }

        model.addAttribute("form", produtosForm);
        return "pedido/formPedido";
    }

    @RequestMapping("/addPedido")
    public String showFormPedido(@ModelAttribute ProdutoPedidoCreationDto form, Model model) {

        // cria novo objeto do pedido adiciona cpf e persiste no banco
        Pedido pedido = new Pedido();
        pedido.setCpfCliente(form.getCpfCliente());
        pedido.setValorTotal(produtoPedidoService.getValorTotal(form.getItens()));
        pedidoService.criarPedido(pedido);

        for (ProdutoPedido produtoPedido : form.getItens()) {
            // seta pedido do produtoPedido
            produtoPedido.setPedido(pedido);

            // adiciona o produtoPedido na lista do produto
            // produtoPedido.getProduto().getProdutosPedido().add(produtoPedido);

            // adiciona o produtoPedido na lista do pedido
            if (produtoPedido.getQuantidade() > 0) {
                pedido.getProdutosPedido().add(produtoPedido);
            }

        }
        try {
            produtoPedidoService.persistListaProdutosPedido(pedido.getProdutosPedido(), pedido.getId());
        } catch (Exception e) {
            pedidoService.deletePedido(pedido);
            model.addAttribute("mensagem", e.getMessage());
            return "pedido/pedidoInvalido";
        }

        // pedido.setValorTotal(produtoPedidoService.getValorTotal(pedido.getProdutosPedido()));

        // System.out.println("id do pedido: " + pedido.getId());
        // Pedido mesmoPedido = pedidoService.getPedidoById(pedido.getId());
        // System.out.println("id do mesmoPedido: " + pedido.getId());
        // mesmoPedido.setCpfCliente(form.getCpfCliente());
        // pedidoService.atualizarPedido(mesmoPedido);

        model.addAttribute("pedido", pedido);
        return "pedido/detalhesPedido";
    }

}
