package com.imd.mercearia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nome;

    @Column
    private double preco;

    @Column
    private int quatidadeEstoque;

    public Produto() {

    }

    public Produto(String nome, double preco, int quantidadeEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quatidadeEstoque = quantidadeEstoque;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuatidadeEstoque() {
        return quatidadeEstoque;
    }

    public void setQuatidadeEstoque(int quatidadeEstoque) {
        this.quatidadeEstoque = quatidadeEstoque;
    }

    @Override
    public String toString() {
        return "Produto [nome: " + nome + ", preço: " + preco
                + ", quantidade em estoque: " + quatidadeEstoque + "]";
    }

}
