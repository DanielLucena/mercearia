package com.imd.mercearia.rest.controllers;

import com.imd.mercearia.model.Remessa;
import com.imd.mercearia.rest.dto.RemessaCreationDTO;
import com.imd.mercearia.service.RemessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/remessa")
public class RemessaController {

    @Autowired
    private RemessaService remessaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Remessa criarRemessa(@RequestBody RemessaCreationDTO dto) {
        return remessaService.criarRemessa(dto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarRemessa(@PathVariable Integer id, @RequestBody Remessa remessa) {
        remessaService.atualizarRemessa(id, remessa);
    }

    @GetMapping("{id}")
    public Remessa getRemessaPorId(@PathVariable Integer id) {
        return remessaService.getRemessaPorId(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarRemessa(@PathVariable Integer id) {
        remessaService.deletarRemessa(id);
    }

    @GetMapping
    public List<Remessa> getTodasRemessas() {
        return remessaService.getTodasRemessas();
    }
}
