package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.imd.mercearia.model.Cliente;
import com.imd.mercearia.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

}
