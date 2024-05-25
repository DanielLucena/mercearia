package com.imd.mercearia.rest.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemessaCreationDTO {
    private Integer fornecedorId;
    private Integer funcionarioId;
    private Set<ItemDto> itens;
}
