package com.imd.mercearia.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.model.BeneficioCliente;
import com.imd.mercearia.model.Pagamento;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.ProdutoPedido;
import com.imd.mercearia.repository.PagamentoRepository;
import com.imd.mercearia.repository.PedidoRepository;
import com.imd.mercearia.rest.dto.InformacoesPedidoDto;
import com.imd.mercearia.rest.dto.PedidoCreationDTO;

@Component
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    BeneficioClienteService beneficioClienteService;

    @Autowired
    ProdutoPedidoService produtoPedidoService;

    @Autowired
    PagamentoRepository pagamentoRepository;

    public List<Pedido> getListaPedidos() {
        return pedidoRepository.findAll();
    }

    public void criarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Pedido getPedidoById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pedido não encontrado."));
    }

    public void deletePedido(Pedido pedido) {
        pedidoRepository.delete(pedido);
    }

    public void atualizarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Pedido processarPedido(PedidoCreationDTO pedidoCreationDto) throws EstoqueInsuficienteException {
        String cpf = pedidoCreationDto.getCpfCliente();

        // Valida itens do pedido
        produtoPedidoService.validaListaProdutos(pedidoCreationDto.getItens());

        // Se possuir CPF associado, pegar ou criar cliente
        BeneficioCliente beneficioCliente = beneficioClienteService.obterOuCriarPorCPF(cpf);

        // Calcular o valor do pedido
        List<ProdutoPedido> produtosLista = produtoPedidoService
                .converteDtoToListProdutoPedido(pedidoCreationDto.getItens(), null);
        double subtotal = produtoPedidoService.getValorTotal(produtosLista);

        // Pegar valor do cashback do cliente (sem descontar ainda)
        double desconto = pedidoCreationDto.isUsandoCashback()
                ? beneficioClienteService.calcularDescontoCashbackCliente(beneficioCliente, subtotal)
                : 0.0;

        // Subtrair o valor do desconto (para cálculo do valor total do pedido)
        double valorTotal = subtotal - desconto;

        // Criar pedido com status PENDENTE
        Pedido pedido = new Pedido();
        pedido.setCpfCliente(cpf);
        pedido.setCashbackGerado(BigDecimal.ZERO);
        pedido.setCashbackUsado(BigDecimal.ZERO);
        pedido.setProdutosPedido(produtosLista);
        pedido.setValorTotal(BigDecimal.valueOf(valorTotal));
        pedido.setStatus(Pedido.StatusPedido.PENDENTE);
        pedidoRepository.save(pedido);

        // salvar itens do pedido
        produtoPedidoService.persistListaProdutosPedido(pedidoCreationDto.getItens(), pedido);

        // Não processar cashback e estoque ainda

        return pedido;
    }

    public void finalizarPedido(Pedido pedido, boolean usandoCashback) throws EstoqueInsuficienteException {
        BeneficioCliente beneficioCliente = beneficioClienteService.obterOuCriarPorCPF(pedido.getCpfCliente());

        // Processar desconto de cashback se o cliente optou por usá-lo
        double desconto = usandoCashback
                ? beneficioClienteService.consomePontosCashbackCliente(beneficioCliente,
                        pedido.getValorTotal().doubleValue())
                : 0.0;
        pedido.setCashbackUsado(BigDecimal.valueOf(desconto));

        // Subtrair produtos do estoque
        produtoPedidoService.decrementaEstoqueProdutos(pedido.getProdutosPedido());

        // Calcular e gerar cashback
        double cashbackGerado = processarCashbackGerado(pedido.getValorTotal().doubleValue() - desconto);
        pedido.setCashbackGerado(BigDecimal.valueOf(cashbackGerado));

        // Incrementar pontos de cashback do cliente
        beneficioClienteService.incrementaPontosCashbackCliente(beneficioCliente, cashbackGerado);

        // Atualizar status do pedido para CONCLUIDO
        pedido.setStatus(Pedido.StatusPedido.CONCLUIDO);
        pedidoRepository.save(pedido);
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

    public List<Pedido> listaPedidosPorFiltro(PedidoCreationDTO dto) {
        Pedido filtro = new Pedido();
        filtro.setCpfCliente(dto.getCpfCliente());
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("valorTotal", "cashbackGerado", "cashbackUsado", "status")
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return converterListaPedidoParaDto(pedidoRepository.findAll(example));
    }

    public InformacoesPedidoDto converterPedidoParaDto(Pedido pedido) {
        return InformacoesPedidoDto
                .builder()
                .id(pedido.getId())
                .cpfCliente(pedido.getCpfCliente())
                .desconto(pedido.getCashbackGerado())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus().name())
                .itens(produtoPedidoService
                        .converterListaProdutoPedidoParaDto(pedido.getProdutosPedido()))
                .build();

    }

    private List<InformacoesPedidoDto> converterListaPedidoParaDto(List<Pedido> pedidos) {
        if (CollectionUtils.isEmpty(pedidos)) {
            return Collections.emptyList();
        }
        return pedidos.stream().map(
                pedido -> converterPedidoParaDto(pedido)).collect(Collectors.toList());
    }
}
