package com.imd.mercearia.service;

import com.imd.mercearia.exception.EstoqueInsuficienteException;
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

    public ItemRemessa adicionarItemRemessa(Integer remessaId, ItemRemessa itemRemessa) {
        Remessa remessa = remessaRepository.findById(remessaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));

        Produto produto = itemRemessa.getProduto();
        validaEstoqueProdutoSuficiente(produto, itemRemessa.getQuantidade());

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemRemessa.getQuantidade());
        produtoRepository.save(produto);

        itemRemessa.setRemessa(remessa);
        return itemRemessaRepository.save(itemRemessa);
    }

    public void atualizarItemRemessa(Integer remessaId, Integer itemId, ItemRemessa itemRemessa) {
        Remessa remessa = remessaRepository.findById(remessaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));

        ItemRemessa itemExistente = itemRemessaRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ItemRemessa não encontrado"));

        int quantidadeDiferenca = itemRemessa.getQuantidade() - itemExistente.getQuantidade();
        Produto produto = itemRemessa.getProduto();

        validaEstoqueProdutoSuficiente(produto, quantidadeDiferenca);

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeDiferenca);
        produtoRepository.save(produto);

        itemRemessa.setId(itemExistente.getId());
        itemRemessa.setRemessa(remessa);
        itemRemessaRepository.save(itemRemessa);
    }

    public ItemRemessa getItemRemessaPorId(Integer remessaId, Integer itemId) {
        Remessa remessa = remessaRepository.findById(remessaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));

        return itemRemessaRepository.findById(itemId)
                .filter(item -> item.getRemessa().getId().equals(remessaId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ItemRemessa não encontrado"));
    }

    public void deletarItemRemessa(Integer remessaId, Integer itemId) {
        Remessa remessa = remessaRepository.findById(remessaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));

        ItemRemessa itemRemessa = itemRemessaRepository.findById(itemId)
                .filter(item -> item.getRemessa().getId().equals(remessaId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ItemRemessa não encontrado"));

        Produto produto = itemRemessa.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + itemRemessa.getQuantidade());
        produtoRepository.save(produto);

        itemRemessaRepository.delete(itemRemessa);
    }

    public List<ItemRemessa> getItensRemessa(Integer remessaId) {
        Remessa remessa = remessaRepository.findById(remessaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remessa não encontrada"));

        return remessa.getItens().stream().toList();
    }

    private void validaEstoqueProdutoSuficiente(Produto produto, int quantidade) {
        if (quantidade > produto.getQuantidadeEstoque()) {
            throw new EstoqueInsuficienteException(produto, quantidade);
        }
    }
}


