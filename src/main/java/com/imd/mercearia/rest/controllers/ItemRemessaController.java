package com.imd.mercearia.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.imd.mercearia.model.ItemRemessa;
import com.imd.mercearia.service.ItemRemessaService;

import java.util.List;

@RestController
@RequestMapping("/api/item-remessa")
public class ItemRemessaController {

    @Autowired
    private ItemRemessaService itemRemessaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRemessa save(@RequestBody ItemRemessa itemRemessa) {
        return itemRemessaService.saveItemRemessa(itemRemessa);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody ItemRemessa itemRemessa) {
        itemRemessaService.getItemRemessaById(id).map(ir -> {
            itemRemessa.setId(ir.getId());
            itemRemessaService.saveItemRemessa(itemRemessa);
            return itemRemessa;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ItemRemessa não encontrado."));
    }

    @GetMapping("{id}")
    public ItemRemessa getById(@PathVariable Integer id) {
        return itemRemessaService.getItemRemessaById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ItemRemessa não encontrado."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        itemRemessaService.getItemRemessaById(id).map(ir -> {
            itemRemessaService.deleteItemRemessa(id);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ItemRemessa não encontrado."));
    }

    @GetMapping
    public List<ItemRemessa> findAll() {
        return itemRemessaService.getAllItensRemessa();
    }
}
