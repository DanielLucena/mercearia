package com.imd.mercearia.exception;

import com.imd.mercearia.model.Cliente;

public class ClienteJaCadastradoException extends RuntimeException {
    public ClienteJaCadastradoException(Cliente cliente) {
        super("Já existe um cliente cadastrado com o cpf: " + cliente.getCpf());
    }
}
