package com.imd.mercearia.rest.dto;

import java.util.Set;

public class RemessaCreationDTO {
    private Integer fornecedorId;
    private Integer funcionarioId;
    private Set<ItemRemessaDTO> itens;

    // Getters and Setters
    public Integer getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Set<ItemRemessaDTO> getItens() {
        return itens;
    }

    public void setItens(Set<ItemRemessaDTO> itens) {
        this.itens = itens;
    }
    public static class ItemRemessaDTO {
        private Integer produtoId;
        private int quantidade;
    
        // Getters and Setters
        public Integer getProdutoId() {
            return produtoId;
        }
    
        public void setProdutoId(Integer produtoId) {
            this.produtoId = produtoId;
        }
    
        public int getQuantidade() {
            return quantidade;
        }
    
        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }
}

