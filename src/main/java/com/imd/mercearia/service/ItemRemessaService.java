package com.imd.mercearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imd.mercearia.model.ItemRemessa;
import com.imd.mercearia.repository.ItemRemessaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRemessaService {

    @Autowired
    private ItemRemessaRepository itemRemessaRepository;

    public List<ItemRemessa> getAllItensRemessa() {
        return itemRemessaRepository.findAll();
    }

    public Optional<ItemRemessa> getItemRemessaById(Integer id) {
        return itemRemessaRepository.findById(id);
    }

    public ItemRemessa saveItemRemessa(ItemRemessa itemRemessa) {
        return itemRemessaRepository.save(itemRemessa);
    }

    public void deleteItemRemessa(Integer id) {
        itemRemessaRepository.deleteById(id);
    }
}
