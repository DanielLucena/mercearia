package com.imd.mercearia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
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
    private List<ProdutoPedido> produtosPedido = new ArrayList<>();

    @Column(precision = 20, scale = 2)
    private BigDecimal cashbackGerado;

    @Column(precision = 20, scale = 2)
    private BigDecimal cashbackUsado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    public enum StatusPedido {
        PENDENTE,
        CONCLUIDO
    }

    public Pedido() {
        this.status = StatusPedido.PENDENTE;
    }

    // getters e setters

    @Override
    public String toString() {
        return "[pedido_id: " + id + ", status: " + status + " ]";
    }
}
