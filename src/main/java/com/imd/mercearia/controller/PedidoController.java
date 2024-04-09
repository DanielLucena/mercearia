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
import com.imd.mercearia.model.BeneficioCliente;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.service.ProdutoPedidoService;
import com.imd.mercearia.service.BeneficioClienteService;
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

    @Autowired
    BeneficioClienteService beneficioClienteService;

    @RequestMapping("/getListaPedidos")
    public String getListaPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.getListaPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedido/listaPedido";
    }

    @RequestMapping("/showForm")
    public String showFormPedido(Model model) {
        ProdutoPedidoCreationDto produtosForm = new ProdutoPedidoCreationDto(new ArrayList<ProdutoPedido>());
        produtosForm.setUsandoCashback(true);
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

        if (beneficioClienteService.getBeneficioClienteByCpf(form.getCpfCliente()) == null) {

            BeneficioCliente beneficioCliente = new BeneficioCliente();
            beneficioCliente.setCpf(form.getCpfCliente());
            beneficioClienteService.salvarBeneficioCliente(beneficioCliente);
        }

        pedido.setValorTotal(produtoPedidoService.getValorTotal(form.getItens()));

        System.out.println("\nusando cashback: " + form.isUsandoCashback() + " \n");
        double cachbackCLiente = 0;
        if (form.isUsandoCashback()) {
            // subistituir
            cachbackCLiente = beneficioClienteService.getBeneficioClienteByCpf(form.getCpfCliente())
                    .getPontosCashback();
        }
        pedido.setCashbackUsado(cachbackCLiente);
        pedido.setCashbackGerado(pedidoService.getCashbackGerado(pedido));
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

        model.addAttribute("pedido", pedido);
        return "pedido/detalhesPedido";
    }

}
