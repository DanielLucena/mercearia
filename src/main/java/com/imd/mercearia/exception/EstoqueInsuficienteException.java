package com.imd.mercearia.exception;

import com.imd.mercearia.model.Produto;
import com.imd.mercearia.model.ProdutoPedido;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(Produto produto, int quantidade) {
        super("Quantidade em estoque insuficiente do produto: " +
                produto.getNome() +
                ". Você colocou no pedido " + quantidade +
                " itens desse produto, porem so existem " + produto.getQuantidadeEstoque() +
                " itens no estoque!");
    }
}
