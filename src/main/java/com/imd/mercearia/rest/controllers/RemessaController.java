package com.imd.mercearia.rest.controllers;

import com.imd.mercearia.model.Remessa;
import com.imd.mercearia.rest.dto.RemessaCreationDTO;
import com.imd.mercearia.rest.dto.RemessaDTO;
import com.imd.mercearia.service.RemessaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/remessa")
@Tag(name = "Remessa")
@CrossOrigin(origins = "*")
public class RemessaController {

    @Autowired
    private RemessaService remessaService;

    @Operation(summary = "Cria uma remessa", method = "POST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Remessa criarRemessa(@RequestBody RemessaCreationDTO dto) {
        return remessaService.salvarRemessa(dto);
    }

    @Operation(summary = "Atualiza uma remessa", method = "PUT")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarRemessa(@PathVariable Integer id, @RequestBody Remessa remessa) {
        remessaService.atualizarRemessa(id, remessa);
    }

    @Operation(summary = "Recupera uma remessa pelo ID", method = "POST")
    @GetMapping("{id}")
    public RemessaDTO getRemessaPorId(@PathVariable Integer id) {
        return remessaService.getRemessaPorId(id);
    }

    @Operation(summary = "Exclui uma remessa", method = "DELETE")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarRemessa(@PathVariable Integer id) {
        remessaService.deletarRemessa(id);
    }

    @Operation(summary = "Retorna a lista de todas as remessas", method = "GET")
    @GetMapping
    public List<Remessa> getTodasRemessas() {
        return remessaService.getTodasRemessas();
    }

}
