package com.imd.mercearia.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
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
        Cliente oldCliente = clienteRepository
                .findById(cliente.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."));

        if (cliente.getCpf() == null) {
            cliente.setCpf(oldCliente.getCpf());
        } else {
            if (beneficioClienteService
                    .existsBeneficioClienteComCpf(cliente.getCpf())
                    && !cliente.getCpf().equals(oldCliente.getCpf())) {
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
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."));
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

    public List<Pedido> buscaPedidosByClienteId(Integer id) {
        Cliente cliente = buscarClientePorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado."));
        return pedidoRepository.getPedidosByCpf(cliente.getCpf());
    }

    public List<Cliente> listaClientesPorfiltro(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example, Sort.by(Sort.Direction.ASC, "id"));
    }
}
