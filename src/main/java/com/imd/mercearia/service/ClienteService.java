package com.imd.mercearia.service;

import java.util.List;
import java.util.Optional;

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
        // busca cliente original cadastrado no banco
        Cliente oldCliente = clienteRepository
                .findById(cliente.getId())
                .orElseThrow(null);

        // certifica que o cpf do cliente não sera alterado para null
        if (cliente.getCpf() == null) {
            cliente.setCpf(oldCliente.getCpf());
        } else {
            // certifica que o novo cpf não esteja em uso
            if (beneficioClienteService
                    .existsBeneficioClienteComCpf(cliente.getCpf())) {
                throw new ClienteJaCadastradoException(cliente);
            }
        }

        BeneficioCliente beneficioCliente = oldCliente.getBeneficioCliente();
        beneficioCliente.setCpf(cliente.getCpf());

        cliente.setBeneficioCliente(beneficioCliente);
        beneficioClienteService.update(beneficioCliente);

        clienteRepository.save(cliente);
    }

    public void deletarCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        beneficioClienteService.deletarBeneficioCliente(cliente.getBeneficioCliente());
        clienteRepository.deleteById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public List<Pedido> buscaPedidosByCliente(String cpf) {
        return pedidoRepository.getPedidosByCpf(cpf);
    }
}
