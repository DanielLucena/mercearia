package com.imd.mercearia.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "beneficios_clientes")
public class BeneficioCliente {
    @Id
    private String cpf;

    private double pontosCashback;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getPontosCashback() {
        return pontosCashback;
    }

    public void setPontosCashback(double pontosCashback) {
        this.pontosCashback = pontosCashback;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
