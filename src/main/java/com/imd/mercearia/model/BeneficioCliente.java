package com.imd.mercearia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "beneficio_cliente")
public class BeneficioCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String cpf;

    private double pontosCashback;

    @OneToOne(mappedBy = "beneficioCliente", cascade = CascadeType.ALL)
    private Cliente cliente;

    public BeneficioCliente() {

    }

    public BeneficioCliente(String cpfString) {
        this.cpf = cpfString;
        this.pontosCashback = 0.;
    }

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

    @Override
    public String toString() {
        return "[cpf: " + cpf + ", pontos cashbabk: " + pontosCashback + "]";
    }

}
