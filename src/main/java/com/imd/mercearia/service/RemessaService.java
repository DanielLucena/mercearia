package com.imd.mercearia.service;

import com.imd.mercearia.model.Remessa;
import com.imd.mercearia.model.ItemRemessa;
import com.imd.mercearia.repository.RemessaRepository;
import com.imd.mercearia.repository.ItemRemessaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemessaService {

    @Autowired
    private RemessaRepository remessaRepository;

    @Autowired
    private ItemRemessaRepository itemRemessaRepository;

    public Remessa criarRemessa(Remessa remessa) {
        // Associa os itens à remessa
        for (ItemRemessa item : remessa.getItens()) {
            item.setRemessa(remessa);
        }
        
        Remessa novaRemessa = remessaRepository.save(remessa);
        itemRemessaRepository.saveAll(remessa.getItens());
        return novaRemessa;
    }

    public Remessa atualizarRemessa(Integer id, Remessa remessa) {
        Remessa remessaExistente = remessaRepository.findById(id).orElseThrow(() -> new RuntimeException("Remessa não encontrada"));
        
        // Atualiza os detalhes da remessa
        remessaExistente.setFornecedor(remessa.getFornecedor());
        remessaExistente.setFuncionario(remessa.getFuncionario());
        
        // Atualiza os itens da remessa
        itemRemessaRepository.deleteAll(remessaExistente.getItens());
        for (ItemRemessa item : remessa.getItens()) {
            item.setRemessa(remessaExistente);
        }
        remessaExistente.setItens(remessa.getItens());
        
        Remessa remessaAtualizada = remessaRepository.save(remessaExistente);
        itemRemessaRepository.saveAll(remessaExistente.getItens());
        return remessaAtualizada;
    }

    public Remessa getRemessaPorId(Integer id) {
        return remessaRepository.findById(id).orElseThrow(() -> new RuntimeException("Remessa não encontrada"));
    }

    public void deletarRemessa(Integer id) {
        Remessa remessa = remessaRepository.findById(id).orElseThrow(() -> new RuntimeException("Remessa não encontrada"));
        itemRemessaRepository.deleteAll(remessa.getItens());
        remessaRepository.delete(remessa);
    }

    public List<Remessa> getTodasRemessas() {
        return remessaRepository.findAll();
    }
}
