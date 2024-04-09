package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.repository.ProdutoPedidoRepository;

@Component
public class ProdutoPedidoService {
    @Autowired
    ProdutoPedidoRepository produtoPedidoRepository;

    public void persistListaProdutosPedido(List<ProdutoPedido> produtos) {
        for (ProdutoPedido produtoPedido : produtos) {
            System.out.println("item do pedido: " + produtoPedido.getPedido().getId());

            produtoPedidoRepository.save(produtoPedido);
            System.out.println(produtoPedido.getId());
        }
    }
}
