package com.imd.mercearia.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import com.imd.mercearia.model.Pagamento.TipoPagamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PedidoCreationDTO {
    private List<ItemDto> itens;
    private String cpfCliente;
    private boolean usandoCashback;
    private List<PagamentoDTO> pagamentos;
}
