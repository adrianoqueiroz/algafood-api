package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("select max(updatedAt) from FormaPagamento")
    OffsetDateTime getLastUpdatedAt();

    @Query("select updatedAt from FormaPagamento where id = :id")
    OffsetDateTime getUpdatedAtById(Long id);
}
