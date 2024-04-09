package com.imd.mercearia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imd.mercearia.model.Fornecedor;
import com.imd.mercearia.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query(value = "select * from produto p order by p.nome", nativeQuery = true)
    List<Produto> getProdutosOrded();

    List<Produto> findByFornecedor(Fornecedor fornecedor);
}
