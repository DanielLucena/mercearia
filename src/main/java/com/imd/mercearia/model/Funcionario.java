package com.imd.mercearia.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
    private Set<Remessa> remessas;

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

    public Set<Remessa> getRemessas() {
        return remessas;
    }

    public void setRemessas(Set<Remessa> remessas) {
        this.remessas = remessas;
    }
}
