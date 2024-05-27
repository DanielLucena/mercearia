package com.imd.mercearia.service;

import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.exception.RegraNegocioException;
import com.imd.mercearia.model.Pagamento;
import com.imd.mercearia.model.Pedido;
import com.imd.mercearia.model.Pedido.StatusPedido;
import com.imd.mercearia.repository.PagamentoRepository;
import com.imd.mercearia.repository.PedidoRepository;
import com.imd.mercearia.rest.dto.PagamentoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    public Pagamento criarPagamento(PagamentoDTO dto) throws EstoqueInsuficienteException {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        if (pedido.getValorTotal().doubleValue() > dto.getValor().doubleValue()) {
            throw new RegraNegocioException("valor insuficiente");
        }
        if (pedidoService.getPedidoById(dto.getPedidoId()).getStatus() == StatusPedido.CONCLUIDO) {
            throw new RegraNegocioException("Pedido já pago!");
        }
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setTipoPagamento(dto.getTipoPagamento());
        pagamento.setTroco(dto.getTroco());
        pagamento.setValor(dto.getValor());
        Pagamento novoPagamento = pagamentoRepository.save(pagamento);

        // Finalizar o pedido (descontar estoque, processar cashback e atualizar status)
        pedidoService.finalizarPedido(pedido, dto.isUsandoCashback());

        return novoPagamento;
    }
}
