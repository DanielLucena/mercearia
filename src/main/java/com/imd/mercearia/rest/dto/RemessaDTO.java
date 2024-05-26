package com.imd.mercearia.rest.dto;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Funcionario;
import com.imd.mercearia.model.ItemRemessa;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemessaDTO {
    private Integer id;
    private Fornecedor fornecedor;
    private Funcionario funcionario;
    private Set<ItemRemessa> itens;
}
