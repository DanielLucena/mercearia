package com.imd.mercearia.service;

import com.imd.mercearia.exception.RegraNegocioException;
import com.imd.mercearia.model.Remessa;
import com.imd.mercearia.model.ItemRemessa;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Funcionario;
import com.imd.mercearia.repository.RemessaRepository;
import com.imd.mercearia.repository.ItemRemessaRepository;
import com.imd.mercearia.repository.FornecedorRepository;
import com.imd.mercearia.repository.FuncionarioRepository;
import com.imd.mercearia.repository.ProdutoRepository;
import com.imd.mercearia.rest.dto.RemessaCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.imd.mercearia.rest.dto.RemessaCreationDTO.ItemRemessaDTO;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RemessaService {

    @Autowired
    private RemessaRepository remessaRepository;

    @Autowired
    private ItemRemessaRepository itemRemessaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Remessa criarRemessa(RemessaCreationDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado."));
        Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado."));

        Remessa remessa = new Remessa();
        remessa.setFornecedor(fornecedor);
        remessa.setFuncionario(funcionario);

        Set<ItemRemessa> itens = new HashSet<>();
        for (ItemRemessaDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
            ItemRemessa itemRemessa = new ItemRemessa();
            itemRemessa.setProduto(produto);
            itemRemessa.setQuantidade(itemDTO.getQuantidade());
            itemRemessa.setRemessa(remessa); // setting the parent remessa
            itens.add(itemRemessa);
        }

        remessa.setItens(itens);
        return remessaRepository.save(remessa);
    }

    public Remessa atualizarRemessa(Integer id, Remessa remessa) {
        Remessa remessaExistente = remessaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));

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
        return remessaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));
    }

    public void deletarRemessa(Integer id) {
        Remessa remessa = remessaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));
        itemRemessaRepository.deleteAll(remessa.getItens());
        remessaRepository.delete(remessa);
    }

    public List<Remessa> getTodasRemessas() {
        return remessaRepository.findAll();
    }
}
