package com.imd.mercearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.service.FornecedorService;
import org.springframework.web.bind.annotation.GetMapping;

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

}
