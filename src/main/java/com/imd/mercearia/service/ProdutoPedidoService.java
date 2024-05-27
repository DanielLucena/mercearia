package com.imd.mercearia.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.repository.ProdutoPedidoRepository;
import com.imd.mercearia.rest.dto.InformacaoItemDto;
import com.imd.mercearia.rest.dto.ItemDto;

@Component
public class ProdutoPedidoService {
    @Autowired
    ProdutoPedidoRepository produtoPedidoRepository;

    @Autowired
    ProdutoService produtoService;

    // @Autowired
    // PedidoService pedidoService;

    public void persistListaProdutosPedido(List<ItemDto> itens, Pedido pedido) {
        validaListaProdutos(itens);

        for (ItemDto item : itens) {
            // System.out.println("item do pedido: " + produtoPedido.getPedido().getId());
            if (item.getQuantidade() > 0) {
                ProdutoPedido produtoPedido = new ProdutoPedido();
                produtoPedido.setPedido(pedido);
                produtoPedido.setProduto(produtoService.getProdutoById(item.getProduto()));
                produtoPedido.setQuantidade(item.getQuantidade());
                produtoPedidoRepository.save(produtoPedido);
                // produtoService.darBaixaAoEstoque(produtoPedido.getProduto(),
                // item.getQuantidade());
                System.out.println(produtoPedido.getId());
            }
        }

    }

    public void validaListaProdutos(List<ItemDto> itens) {

        for (ItemDto item : itens) {
            produtoService.validaEstoqueProdutoSuficiente(item.getProduto(), item.getQuantidade());

        }

    }

    public List<ProdutoPedido> converteDtoToListProdutoPedido(List<ItemDto> itens, Pedido pedido) {
        List<ProdutoPedido> produtos = new ArrayList<>();
        for (ItemDto item : itens) {
            ProdutoPedido produtoPedido = new ProdutoPedido();
            produtoPedido.setPedido(pedido);
            produtoPedido.setProduto(produtoService.getProdutoById(item.getProduto()));
            produtoPedido.setQuantidade(item.getQuantidade());
            produtos.add(produtoPedido);
        }
        return produtos;
    }

    public double getValorTotal(List<ProdutoPedido> produtoPedidos) {
        double valorTotal = 0;
        for (ProdutoPedido produtoPedido : produtoPedidos) {
            if (produtoPedido.getQuantidade() > 0) {
                valorTotal += produtoPedido.getQuantidade() * produtoPedido.getProduto().getPreco();
            }

        }
        return valorTotal;
    }

    public List<InformacaoItemDto> converterListaProdutoPedidoParaDto(List<ProdutoPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream().map(
                item -> InformacaoItemDto
                        .builder()
                        .codigoProduto(item.getProduto().getId())
                        .nome(item.getProduto().getNome())
                        .quantidade(item.getQuantidade())
                        .preco(item.getQuantidade() * item.getProduto().getPreco())
                        .build())
                .collect(Collectors.toList());
    }

    public void decrementaEstoqueProdutos(List<ProdutoPedido> itens) {
        for (ProdutoPedido item : itens) {
            produtoService.darBaixaAoEstoque(item.getProduto(), item.getQuantidade());
        }
    }
}
