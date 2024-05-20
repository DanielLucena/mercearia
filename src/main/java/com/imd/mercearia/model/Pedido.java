package com.imd.mercearia.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String cpfCliente;

    @Column(precision = 20, scale = 2)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "pedido")
    List<ProdutoPedido> produtosPedido = new ArrayList<ProdutoPedido>();

    @Column(precision = 20, scale = 2)
    private BigDecimal cashbackGerado;

    @Column(precision = 20, scale = 2)
    private BigDecimal cashbackUsado;

    public Pedido() {

    }

    public Pedido(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ProdutoPedido> getProdutosPedido() {
        return produtosPedido;
    }

    public BigDecimal getCashbackGerado() {
        return cashbackGerado;
    }

    public void setCashbackGerado(BigDecimal cashbackGerado) {
        this.cashbackGerado = cashbackGerado;
    }

    public BigDecimal getCashbackUsado() {
        return cashbackUsado;
    }

    public void setCashbackUsado(BigDecimal cashbackUsado) {
        this.cashbackUsado = cashbackUsado;
    }

    public void setProdutosPedido(List<ProdutoPedido> produtosPedido) {
        this.produtosPedido = produtosPedido;
    }

    @Override
    public String toString() {
        return "[pedido_id: " + id + " ]";
    }
}
