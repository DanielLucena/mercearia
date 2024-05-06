package com.imd.mercearia.exception;

import com.imd.mercearia.model.ProdutoPedido;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(ProdutoPedido produtoPedido) {
        super("Quantidade em estoque insuficiente do produto: " +
                produtoPedido.getProduto().getNome() +
                ". Você colocou no pedido " + produtoPedido.getQuantidade() +
                " itens desse produto, porem so existem " + produtoPedido.getProduto().getQuantidadeEstoque() +
                " itens no estoque!");
    }
}
