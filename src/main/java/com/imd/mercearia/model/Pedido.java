package com.imd.mercearia.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    List<ProdutoPedido> produtosPedido = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    @Column(precision = 20, scale = 2)
    private BigDecimal cashbackGerado;

    @Column(precision = 20, scale = 2)
    private BigDecimal cashbackUsado;

    public Pedido() {}

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

    public void setProdutosPedido(List<ProdutoPedido> produtosPedido) {
        this.produtosPedido = produtosPedido;
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

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    @Override
    public String toString() {
        return "[pedido_id: " + id + " ]";
    }
}
