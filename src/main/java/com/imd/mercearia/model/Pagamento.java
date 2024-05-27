package com.imd.mercearia.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
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

    public Pagamento() {
    }

    public Pagamento(TipoPagamento tipoPagamento, BigDecimal valor, BigDecimal troco, Pedido pedido) {
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
        this.troco = troco;
        this.pedido = pedido;
        processarPagamento();
    }

    private void processarPagamento() {
        if (valor.compareTo(pedido.getValorTotal()) >= 0) {
            pedido.setStatus(Pedido.StatusPedido.CONCLUIDO);
        }
    }

    // getters e setters

    public enum TipoPagamento {
        CARTAO_CREDITO,
        CARTAO_DEBITO,
        PIX,
        DINHEIRO
    }
}
