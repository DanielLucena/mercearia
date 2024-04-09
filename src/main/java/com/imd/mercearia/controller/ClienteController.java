package com.imd.mercearia.controller;

import java.util.List;

import com.imd.mercearia.model.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imd.mercearia.model.Cliente;
import com.imd.mercearia.service.ClienteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/clientes")
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
        return "formCliente";
    }

    @PostMapping("/novo")
    public String salvarNovoCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.salvarCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/detalhes")
    public String detalhesCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "detalhesCliente";
    }

    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "formCliente";
    }

    @PostMapping("/{id}/editar")
    public String salvarClienteEditado(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente) {
        cliente.setId(id);
        clienteService.salvarCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/deletar")
    public String deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return "redirect:/clientes";
    }
}
