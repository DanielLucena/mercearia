package com.imd.mercearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imd.mercearia.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {}
