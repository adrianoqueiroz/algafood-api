package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {

  public List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//  public List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

//  @Query("select r from Restaurante r where r.nome like %:nome% and r.cozinha.id = :cozinhaId")
  public List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId);

  public Optional<Restaurante> findFirstByNomeContaining(String nome);

  public List<Restaurante> findTop2ByNomeContaining(String nome);

  public int countByCozinhaId(Long cozinhaId);

  public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
