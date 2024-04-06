package com.imd.mercearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.service.FornecedorService;
import com.imd.mercearia.service.ProdutoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;

    @Autowired
    FornecedorService fornecedorService;

    @GetMapping("/getListaProdutos")
    public String getListaProdutos(Model model) {
        List<Produto> produtos = produtoService.getListaProdutos();
        List<Fornecedor> fornecedores = fornecedorService.getListaFornecedores(); // Método hipotético para obter fornecedores
        model.addAttribute("produtos", produtos);
        model.addAttribute("fornecedores", fornecedores);
        return "produto/listaProduto";
    }

    @GetMapping("/criar")
    public String formularioProduto(Model model) {
        List<Fornecedor> fornecedores = fornecedorService.getListaFornecedores(); // Método hipotético para obter fornecedores
        model.addAttribute("fornecedores", fornecedores);
        return "produto/criarProduto";
    }

    @PostMapping("/novo")
    public String criarProduto(@ModelAttribute Produto produto) {
        produtoService.criarProduto(produto);
        return "redirect:/produto/getListaProdutos";
    }

}
