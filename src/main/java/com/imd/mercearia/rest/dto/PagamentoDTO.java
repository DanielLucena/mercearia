package com.imd.mercearia.rest.dto;

import java.math.BigDecimal;

import com.imd.mercearia.model.Pagamento.TipoPagamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PagamentoDTO {
    private TipoPagamento tipoPagamento; 
    private BigDecimal valor;
    private BigDecimal troco;
}