package com.imd.mercearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imd.mercearia.model.Cliente;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.service.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/getListaCliente")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "cliente/listaCliente";
    }

    @GetMapping("/novo")
    public String formularioNovoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/formCliente";
    }

    @PostMapping("/salvar")
    public String salvarNovoCliente(@ModelAttribute("cliente") Cliente cliente, RedirectAttributes redirectAttributes) {
        Cliente clienteSalvo = clienteService.salvarCliente(cliente);
        redirectAttributes.addAttribute("id", clienteSalvo.getId());
        return "redirect:/cliente/detalhes/{id}";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesCliente(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "cliente/detalhesCliente";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "cliente/editarCliente";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarCliente(@PathVariable("id") Integer id, @ModelAttribute("cliente") Cliente cliente) {
        clienteService.atualizarCliente(cliente);
        return "redirect:/cliente/getListaCliente";
    }

    @PostMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.deletarCliente(id);
            redirectAttributes.addFlashAttribute("sucesso", "Cliente excluído com sucesso.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/cliente/getListaCliente";
    }

    @GetMapping("/pedidos/{id}")
    public String getMethodName(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        List<Pedido> pedidos = clienteService.buscaPedidosByCliente(cliente.getCpf());
        model.addAttribute("pedidos", pedidos);
        return "pedido/listaPedido";
    }

}
