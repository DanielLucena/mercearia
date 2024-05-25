package com.imd.mercearia.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPagamento tipoPagamento;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(precision = 20, scale = 2)
    private BigDecimal troco;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public Pagamento() {}

    public Pagamento(TipoPagamento tipoPagamento, BigDecimal valor, BigDecimal troco) {
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
        this.troco = troco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }

    public enum TipoPagamento {
        CARTAO_CREDITO,
        CARTAO_DEBITO,
        PIX,
        DINHEIRO
    }
   
}




