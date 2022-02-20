package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
    JpaSpecificationExecutor<Restaurante> {

  @Query("from Restaurante r join r.cozinha join fetch r.formasPagamento")
  List<Restaurante> findAll();

  List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

  List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId);

  Optional<Restaurante> findFirstByNomeContaining(String nome);

  List<Restaurante> findTop2ByNomeContaining(String nome);

  int countByCozinhaId(Long cozinhaId);

  List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
