package com.imd.mercearia.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoItemDto {
    private Integer codigoProduto;
    private String nome;
    private int quantidade;
    private double preco;
}
