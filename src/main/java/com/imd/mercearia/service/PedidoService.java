package com.imd.mercearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.model.BeneficioCliente;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.repository.PedidoRepository;
import com.imd.mercearia.rest.dto.PedidoCreationDto;

@Component
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    BeneficioClienteService beneficioClienteService;

    @Autowired
    ProdutoPedidoService produtoPedidoService;

    public List<Pedido> getListaPedidos() {
        return pedidoRepository.findAll();
    }

    public void criarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Pedido getPedidoById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void deletePedido(Pedido pedido) {
        pedidoRepository.delete(pedido);
    }

    public void atualizarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public double getCashbackGerado(Pedido pedido) {
        return (pedido.getValorTotal() - pedido.getCashbackGerado()) * 0.03;
    }

    public Pedido processarPedido(PedidoCreationDto pedidoCreationDto) throws EstoqueInsuficienteException {
        String cpf = pedidoCreationDto.getCpfCliente();
        System.out.println("!!!usando cashback? " + pedidoCreationDto.isUsandoCashback());

        // valida itens do pedido
        // throws exception
        produtoPedidoService.validaListaProdutos(pedidoCreationDto.getItens());

        // se possuir cpf associado pegar ou criar cliente
        BeneficioCliente beneficioCliente = beneficioClienteService.obterOuCriarPorCPF(cpf);

        // calcular o valor do pedido
        List<ProdutoPedido> produtosLista = produtoPedidoService
                .converteDtoToListProdutoPedido(
                        pedidoCreationDto.getItens(),
                        null);
        double subtotal = produtoPedidoService.getValorTotal(produtosLista);

        // pegar valor do cashback do cliente
        double desconto = getCashbackUsado(cpf, pedidoCreationDto.isUsandoCashback(), subtotal);

        // subtrair o valor do desconto
        double valorTotal = subtotal - desconto;

        // calcular o valor do cashbackgerado
        double cashbackGerado = processarCashbackGerado(valorTotal);

        // salvar pedido
        Pedido pedido = new Pedido();
        pedido.setCpfCliente(cpf);
        pedido.setCashbackGerado(cashbackGerado);
        pedido.setCashbackUsado(desconto);
        pedido.setProdutosPedido(produtosLista);
        pedido.setValorTotal(valorTotal);
        pedidoRepository.save(pedido);

        // salvar itens do pedido
        produtoPedidoService.persistListaProdutosPedido(pedidoCreationDto.getItens(), pedido);

        // salvar cashback na conta do cliente
        beneficioClienteService.incrementaPontosCashbackCliente(beneficioCliente, cashbackGerado);

        return pedido;
    }

    public double getCashbackUsado(String cpfCliente, boolean isUsandoCashback, double subtotal) {
        // saber se esta usando cashback
        if (isUsandoCashback) {
            BeneficioCliente beneficioCliente = beneficioClienteService
                    .obterOuCriarPorCPF(cpfCliente);
            return beneficioClienteService.consomePontosCashbackCliente(beneficioCliente, subtotal);
        }
        return 0.;
    }

    private double processarCashbackGerado(double valorTotal) {
        return valorTotal * 0.03;
    }
}
