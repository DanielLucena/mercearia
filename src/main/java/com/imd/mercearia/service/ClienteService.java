package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.exception.ClienteJaCadastradoException;
import com.imd.mercearia.model.BeneficioCliente;
import com.imd.mercearia.model.Cliente;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.repository.ClienteRepository;
import com.imd.mercearia.repository.PedidoRepository;

@Component
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BeneficioClienteService beneficioClienteService;

    public Cliente salvarCliente(Cliente cliente) {
        BeneficioCliente beneficioCliente = beneficioClienteService.obterOuCriarPorCPF(cliente.getCpf());
        if (beneficioCliente.getCliente() != null) {
            throw new ClienteJaCadastradoException(cliente);
        }
        cliente.setBeneficioCliente(beneficioCliente);
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

    public List<Pedido> buscaPedidosByCliente(String cpf) {
        return pedidoRepository.getPedidosByCpf(cpf);
    }
}
