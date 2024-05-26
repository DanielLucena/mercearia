package com.imd.mercearia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "beneficio_cliente_id")
    private BeneficioCliente beneficioCliente;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BeneficioCliente getBeneficioCliente() {
        return beneficioCliente;
    }

    public void setBeneficioCliente(BeneficioCliente beneficioCliente) {
        this.beneficioCliente = beneficioCliente;
    }
}
