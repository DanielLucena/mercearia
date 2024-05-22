package com.imd.mercearia.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PedidoCreationDto {
    private List<ItemDto> itens;

    private String cpfCliente;

    private boolean usandoCashback;

}
