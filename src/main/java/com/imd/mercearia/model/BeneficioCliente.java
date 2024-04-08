package com.imd.mercearia.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "beneficios_clientes")
public class BeneficioCliente {
    @Id
    private String cpf;

    private int pontosCashback;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getPontosCashback() {
        return pontosCashback;
    }

    public void setPontosCashback(int pontosCashback) {
        this.pontosCashback = pontosCashback;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
