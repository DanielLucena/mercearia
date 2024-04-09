package com.imd.mercearia.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProdutoPedidoKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "produto_id")
    private Integer produtoId;

    @Column(name = "pedido_id")
    private Integer pedidoId;

    public ProdutoPedidoKey() {

    }

    public ProdutoPedidoKey(Integer produtoId, Integer pedidoId) {
        super();
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((produtoId == null) ? 0 : produtoId.hashCode());
        result = prime * result
                + ((pedidoId == null) ? 0 : pedidoId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProdutoPedidoKey other = (ProdutoPedidoKey) obj;
        return Objects.equals(getProdutoId(), other.getProdutoId())
                && Objects.equals(getPedidoId(), other.getPedidoId());
    }

    @Override
    public String toString() {
        return "{produto_id: " + produtoId + ", pedido_id: " + pedidoId + " }";
    }
}
