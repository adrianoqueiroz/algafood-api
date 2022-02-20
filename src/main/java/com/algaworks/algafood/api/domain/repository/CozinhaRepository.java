package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

  List<Cozinha> findAllByNome(String nome);
  Optional<Cozinha> findByNomeContainingIgnoreCase(String nome);
}
