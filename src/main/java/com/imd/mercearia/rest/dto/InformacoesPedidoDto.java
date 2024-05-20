package com.imd.mercearia.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacoesPedidoDto {
    private Integer id;
    private String cpfCliente;
    private List<InformacaoItemDto> itens;
    private double desconto;
    private double valorTotal;

}
