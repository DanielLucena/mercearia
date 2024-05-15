package com.imd.mercearia.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoCreationDTO {
    private String nome;
    private double preco;
    private Integer fornecedor;
    private Integer quantidade;

}
