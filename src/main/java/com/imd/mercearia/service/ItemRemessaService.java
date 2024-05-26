package com.imd.mercearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.imd.mercearia.model.ItemRemessa;
import com.imd.mercearia.model.Produto;
import com.imd.mercearia.model.Remessa;
import com.imd.mercearia.repository.ItemRemessaRepository;
import com.imd.mercearia.repository.ProdutoRepository;
import com.imd.mercearia.rest.dto.ItemDto;
import com.imd.mercearia.exception.EstoqueInsuficienteException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemRemessaService {

    @Autowired
    private ItemRemessaRepository itemRemessaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

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

    public Set<ItemRemessa> persistSetRemessa(Set<ItemDto> itens, Remessa remessa) {
        // validade lista de itens sem perssistir
        validaSetItens(itens);

        Set<ItemRemessa> itensRemessa = new HashSet<ItemRemessa>();
        for (ItemDto itemDto : itens) {
            Produto produto = produtoService.getProdutoById(itemDto.getProduto());
            int quantidade = itemDto.getQuantidade();
            ItemRemessa item = ItemRemessa.builder()
                    .produto(produto)
                    .remessa(remessa)
                    .quantidade(quantidade)
                    .build();
            if (item.getQuantidade() >= 0) {
                itemRemessaRepository.save(item);
                itensRemessa.add(item);
            }

            // incrementa quantidade em produto ja valida dentro dessa função se a
            // quantidade é maior que 0
            produtoService.adicionarAoEstoque(produto, quantidade);
        }
        return itensRemessa;
    }

    public void validaSetItens(Set<ItemDto> itens) {
        for (ItemDto item : itens) {
            produtoService.getProdutoById(item.getProduto());
        }
    }
}
