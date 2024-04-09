package com.imd.mercearia.dto;

import java.util.List;

import com.imd.mercearia.model.ProdutoPedido;

public class ProdutoPedidoCreationDto {
    private List<ProdutoPedido> itens;

    private String cpfCliente;

    public ProdutoPedidoCreationDto() {

    }

    public ProdutoPedidoCreationDto(List<ProdutoPedido> itens) {
        this.itens = itens;
    }

    public void addItem(ProdutoPedido itenPedido) {
        this.itens.add(itenPedido);
    }

    public List<ProdutoPedido> getItens() {
        return itens;
    }

    public void setItens(List<ProdutoPedido> itens) {
        this.itens = itens;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

}
