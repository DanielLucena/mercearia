package com.imd.mercearia.model;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "fornecedor", fetch = FetchType.LAZY)
    private Set<Produto> produtos;

    @JsonIgnore
    @OneToMany(mappedBy = "fornecedor", fetch = FetchType.LAZY)
    private Set<Remessa> remessas;

    // Getters and Setters

    public Fornecedor() {

    }

    public Fornecedor(String nome) {
        this.nome = nome;
    }
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

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Fornecedor [ " + this.nome + ", id = " + this.id + "]";
    }
    public Set<Remessa> getRemessas() {
        return remessas;
    }

    public void setRemessas(Set<Remessa> remessas) {
        this.remessas = remessas;
    }
}
