package com.imd.mercearia.rest.controllers;

import com.imd.mercearia.model.Pagamento;
import com.imd.mercearia.rest.dto.PagamentoDTO;
import com.imd.mercearia.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@RequestBody PagamentoDTO pagamento) {
        Pagamento novoPagamento = pagamentoService.criarPagamento(pagamento);
        return ResponseEntity.ok(novoPagamento);
    }
}
