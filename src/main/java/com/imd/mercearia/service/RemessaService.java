package com.imd.mercearia.service;

import com.imd.mercearia.exception.EstoqueInsuficienteException;
import com.imd.mercearia.exception.RegistroNaoEncontradoException;
import com.imd.mercearia.exception.RegraNegocioException;
import com.imd.mercearia.model.Remessa;
import com.imd.mercearia.model.ItemRemessa;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Funcionario;
import com.imd.mercearia.repository.RemessaRepository;
import com.imd.mercearia.repository.ItemRemessaRepository;
import com.imd.mercearia.repository.ProdutoRepository;
import com.imd.mercearia.rest.dto.RemessaCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class RemessaService {

    @Autowired
    private RemessaRepository remessaRepository;

    @Autowired
    private ItemRemessaRepository itemRemessaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemRemessaService itemRemessaService;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private FuncionarioService funcionarioService;

    public Remessa salvarRemessa(RemessaCreationDTO dto) {
        Fornecedor fornecedor = fornecedorService.getFornecedorPorId(dto.getFornecedorId())
                .orElseThrow(() -> new RegraNegocioException("Código de fornecedor inválido"));
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(dto.getFuncionarioId())
                .orElseThrow(() -> new RegraNegocioException("Código de funcionario inválido"));
        Remessa remessa = new Remessa();
        remessa.setFornecedor(fornecedor);
        remessa.setFuncionario(funcionario);

        remessaRepository.save(remessa);

        try {
            Set<ItemRemessa> ItensRemessa = itemRemessaService.persistSetRemessa(dto.getItens(), remessa);
            remessa.setItens(ItensRemessa);
            return remessa;
        } catch (RegistroNaoEncontradoException e) {
            throw new RegraNegocioException(e.getMessage());
        }

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
