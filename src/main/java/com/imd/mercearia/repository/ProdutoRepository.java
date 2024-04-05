package com.imd.mercearia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imd.mercearia.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    // @Query(value = "selec e.* from produto order by name")
    // List<Produto> getListaProdutosPorNome();
}
