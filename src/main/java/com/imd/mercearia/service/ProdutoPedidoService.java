package com.imd.mercearia.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.repository.ProdutoPedidoRepository;

@Component
public class ProdutoPedidoService {
    @Autowired
    ProdutoPedidoRepository produtoPedidoRepository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    PedidoService pedidoService;

    public void persistListaProdutosPedido(Set<ProdutoPedido> produtos, Integer pedido_id) throws Exception {
        validaListaProdutos(produtos);

        for (ProdutoPedido produtoPedido : produtos) {
            System.out.println("item do pedido: " + produtoPedido.getPedido().getId());
            if (produtoPedido.getQuantidade() > 0) {
                produtoPedidoRepository.save(produtoPedido);
                produtoService.darBaixaAoEstoque(produtoPedido.getProduto(),
                        produtoPedido.getQuantidade());
            }

            System.out.println(produtoPedido.getId());
        }
    }

    public void validaListaProdutos(Set<ProdutoPedido> produtos) throws Exception {
        for (ProdutoPedido produtoPedido : produtos) {
            if (produtoPedido.getQuantidade() > produtoPedido.getProduto().getQuantidadeEstoque()) {
                throw new Exception("Quantidade em estoque insuficiente do produto: " +
                        produtoPedido.getProduto().getNome() +
                        ". Você colocou no pedido " + produtoPedido.getQuantidade() +
                        " itens desse produto, porem so existem " + produtoPedido.getProduto().getQuantidadeEstoque() +
                        " itens no estoque!");
            }
        }
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
}
