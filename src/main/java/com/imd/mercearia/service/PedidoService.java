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
        pedido.setCashbackGerado(BigDecimal.valueOf(cashbackGerado));
        pedido.setCashbackUsado(BigDecimal.valueOf(desconto));
        pedido.setProdutosPedido(produtosLista);
        pedido.setValorTotal(BigDecimal.valueOf(valorTotal));
         // Processar pagamento
        List<Pagamento> pagamentos = pedidoCreationDto.getPagamentos().stream()
                .map(dto -> new Pagamento(dto.getTipoPagamento(), dto.getValor(), dto.getTroco()))
                .collect(Collectors.toList());
        pedido.setPagamentos(pagamentos);
        
        // pedidoRepository.save(pedido);
        pagamentoRepository.saveAll(pagamentos);
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

    public List<Pedido> listaPedidosPorFiltro(PedidoCreationDTO dto) {
        Pedido filtro = new Pedido();
        filtro.setCpfCliente(dto.getCpfCliente());
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("valorTotal", "cashbackGerado", "cashbackUsado")
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
