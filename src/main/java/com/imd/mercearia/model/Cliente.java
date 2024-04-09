package com.imd.mercearia.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private BeneficioCliente beneficioCliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
