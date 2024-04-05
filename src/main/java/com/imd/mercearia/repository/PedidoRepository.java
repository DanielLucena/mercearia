package com.imd.mercearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.mercearia.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
