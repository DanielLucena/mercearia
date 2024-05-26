package com.imd.mercearia.repository;

import com.imd.mercearia.model.Remessa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RemessaRepository extends JpaRepository<Remessa, Integer> {

    @EntityGraph(attributePaths = "itens")
    Optional<Remessa> findById(Integer id);
}
