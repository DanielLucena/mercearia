package com.imd.mercearia.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {

    @Getter
    private List<String> errors;

    // lista de erros
    public ApiErrors(String mensagemErro) {
        this.errors = Arrays.asList(mensagemErro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
