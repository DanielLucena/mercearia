package com.imd.mercearia.exception;

public class RegistroNaoEncontradoException extends RuntimeException {
    public RegistroNaoEncontradoException(Class<?> entidade, Integer id) {
        super("Registro de " + entidade.getSimpleName()
                + " com id: " + id
                + " não foi encontrado no bando de dados");
    }
}
