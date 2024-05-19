package com.imd.mercearia.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imd.mercearia.exception.ClienteJaCadastradoException;
import com.imd.mercearia.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(ClienteJaCadastradoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrors handleClienteJaCadastradoException(ClienteJaCadastradoException ex) {
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
}
