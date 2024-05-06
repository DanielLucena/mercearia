package com.imd.mercearia.model;

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

    @Column
    private double valorTotal;

    @OneToMany(mappedBy = "pedido")
    List<ProdutoPedido> produtosPedido = new ArrayList<ProdutoPedido>();

    @Column
    private double cashbackGerado;

    @Column
    private double cashbackUsado;

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

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ProdutoPedido> getProdutosPedido() {
        return produtosPedido;
    }

    public double getCashbackGerado() {
        return cashbackGerado;
    }

    public void setCashbackGerado(double cashbackGerado) {
        this.cashbackGerado = cashbackGerado;
    }

    public double getCashbackUsado() {
        return cashbackUsado;
    }

    public void setCashbackUsado(double cashbackUsado) {
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
