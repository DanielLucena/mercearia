package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.repository.ProdutoPedidoRepository;

@Component
public class ProdutoPedidoService {
    @Autowired
    ProdutoPedidoRepository produtoPedidoRepository;

    @Autowired
    ProdutoService produtoService;

    // @Autowired
    // PedidoService pedidoService;

    public void persistListaProdutosPedido(List<ProdutoPedido> produtos, Pedido Pedido)
            throws EstoqueInsuficienteException {
        validaListaProdutos(produtos);

        for (ProdutoPedido produtoPedido : produtos) {
            // System.out.println("item do pedido: " + produtoPedido.getPedido().getId());
            if (produtoPedido.getQuantidade() > 0) {
                produtoPedido.setPedido(Pedido);
                produtoPedidoRepository.save(produtoPedido);
                produtoService.darBaixaAoEstoque(produtoPedido.getProduto(),
                        produtoPedido.getQuantidade());
            }

            System.out.println(produtoPedido.getId());
        }
    }

    public void validaListaProdutos(List<ProdutoPedido> produtos) throws EstoqueInsuficienteException {
        for (ProdutoPedido produtoPedido : produtos) {
            if (produtoPedido.getQuantidade() > produtoPedido.getProduto().getQuantidadeEstoque()) {
                throw new EstoqueInsuficienteException(produtoPedido);
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
