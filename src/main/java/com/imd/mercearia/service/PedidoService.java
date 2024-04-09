package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.repository.PedidoRepository;

@Component
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    public List<Pedido> getListaPedidos() {
        return pedidoRepository.findAll();
    }

    public void criarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Pedido getPedidoById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void deletePedido(Pedido pedido) {
        pedidoRepository.delete(pedido);
    }
}
