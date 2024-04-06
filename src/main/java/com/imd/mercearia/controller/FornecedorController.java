package com.imd.mercearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.service.FornecedorService;
import com.imd.mercearia.service.ProdutoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/getListaFornecedores")
    public String showListaFornecedor(Model model) {
        List<Fornecedor> fornecedores = fornecedorService.getListaFornecedores();
        model.addAttribute("fornecedores", fornecedores);
        return "fornecedor/listaFornecedor";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor()); // Adiciona um novo fornecedor ao modelo
        return "fornecedor/criarFornecedor"; // Retorna o nome da página do formulário de criação
    }


    @PostMapping("/criar")
    public String criarFornecedor(@ModelAttribute("fornecedor") Fornecedor fornecedor, RedirectAttributes redirectAttributes) {
        fornecedorService.criarFornecedor(fornecedor);
        return "redirect:/fornecedor/getListaFornecedores";
    }

    @GetMapping("/detalhes/{id}")
    public String mostrarDetalhes(@PathVariable("id") Integer id, Model model) {
        Fornecedor fornecedor = fornecedorService.getFornecedorPorId(id);
        List<Produto> produtos = produtoService.getProdutosPorFornecedor(fornecedor);
        if (produtos.isEmpty()) {
            model.addAttribute("mensagem", "Nenhum produto encontrado para este fornecedor.");
        } else {
            model.addAttribute("produtos", produtos);
        }
        model.addAttribute("fornecedor", fornecedor);
        // model.addAttribute("produtos", produtos);
        return "fornecedor/detalhesFornecedor";
    }

     @GetMapping("/editar/{id}")
    public String editarFornecedor(@PathVariable("id") Integer id, Model model) {
        Fornecedor fornecedor = fornecedorService.getFornecedorPorId(id);
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedor/editarFornecedor";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarFornecedor(@PathVariable("id") Integer id, @ModelAttribute("fornecedor") Fornecedor fornecedor) {

        fornecedorService.atualizarFornecedor(fornecedor);
        return "redirect:/fornecedor/getListaFornecedores";
    }

    @PostMapping("/deletar/{id}")
    public String deletarFornecedor(@PathVariable("id") Integer id) {
        fornecedorService.deletarFornecedor(id);
        return "redirect:/fornecedor/getListaFornecedores";
    }

}
