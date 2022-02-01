package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
