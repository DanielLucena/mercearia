package com.imd.mercearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.model.ProdutoPedidoKey;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, ProdutoPedidoKey> {

}
