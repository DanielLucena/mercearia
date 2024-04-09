package com.imd.mercearia.service;

import java.util.List;

import com.imd.mercearia.model.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.imd.mercearia.model.Cliente;
import com.imd.mercearia.repository.ClienteRepository;

@Component
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void atualizarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }


    public void deletarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }
}
