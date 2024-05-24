package com.imd.mercearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.model.ItemRemessa;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.repository.ItemRemessaRepository;
import com.imd.mercearia.repository.ProdutoRepository;
import com.imd.mercearia.exception.EstoqueInsuficienteException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRemessaService {

    @Autowired
    private ItemRemessaRepository itemRemessaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ItemRemessa> getAllItensRemessa() {
        return itemRemessaRepository.findAll();
    }

    public Optional<ItemRemessa> getItemRemessaById(Integer id) {
        return itemRemessaRepository.findById(id);
    }

    public ItemRemessa saveItemRemessa(ItemRemessa itemRemessa) {
        Produto produto = itemRemessa.getProduto();
        int quantidade = itemRemessa.getQuantidade();

        validaEstoqueProdutoSuficiente(produto, quantidade);

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        produtoRepository.save(produto);

        return itemRemessaRepository.save(itemRemessa);
    }

    public ItemRemessa updateItemRemessa(Integer id, ItemRemessa itemRemessa) {
        return getItemRemessaById(id).map(existingItem -> {
            int quantidadeDiferenca = itemRemessa.getQuantidade() - existingItem.getQuantidade();
            Produto produto = itemRemessa.getProduto();

            validaEstoqueProdutoSuficiente(produto, quantidadeDiferenca);

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeDiferenca);
            produtoRepository.save(produto);

            itemRemessa.setId(existingItem.getId());
            return itemRemessaRepository.save(itemRemessa);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ItemRemessa não encontrado."));
    }

    public void deleteItemRemessa(Integer id) {
        getItemRemessaById(id).ifPresent(itemRemessa -> {
            Produto produto = itemRemessa.getProduto();
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + itemRemessa.getQuantidade());
            produtoRepository.save(produto);

            itemRemessaRepository.deleteById(id);
        });
    }

    private void validaEstoqueProdutoSuficiente(Produto produto, int quantidade) {
        if (quantidade > produto.getQuantidadeEstoque()) {
            throw new EstoqueInsuficienteException(produto, quantidade);
        }
    }
}
