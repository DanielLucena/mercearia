package com.imd.mercearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.service.FornecedorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorService fornecedorService;

    @GetMapping("/getListaFornecedores")
    public String showListaFornecedor(Model model) {
        List<Fornecedor> fornecedores = fornecedorService.getListaFornecedores();
        model.addAttribute("fornecedores", fornecedores);
        return "fornecedor/listaFornecedor";
    }

     @PostMapping("/criar")
    public String criarFornecedor(@ModelAttribute("fornecedor") Fornecedor fornecedor, RedirectAttributes redirectAttributes) {
        Fornecedor novoFornecedor = fornecedorService.criarFornecedor(fornecedor);
        redirectAttributes.addAttribute("id", novoFornecedor.getId());
        return "redirect:/fornecedor/detalhes";
    }

    @GetMapping("/detalhes")
    public String mostrarDetalhes(@RequestParam("id") Integer id, Model model) {
        Fornecedor fornecedor = fornecedorService.getFornecedorPorId(id);
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedor/detalhes";
    }

}
