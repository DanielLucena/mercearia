package com.imd.mercearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imd.mercearia.model.Produto;
import com.imd.mercearia.service.ProdutoService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;

    @GetMapping("/getListaProdutos")
    public String getListaProdutos(Model model) {
        List<Produto> produtos = produtoService.getListaProdutos();
        model.addAttribute("produtos", produtos);
        return "produto/listaProduto";
    }

}
