package com.algaworks.algafood.api.infrastructure.repository;

import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.repository.RestauranteRepositoryQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
    var parametros = new HashMap<String, Object>();

    var jpql = new StringBuilder();
    jpql.append("select r from Restaurante r ");
    jpql.append("where 1 = 1 ");

    if (StringUtils.hasLength(nome)) {
      jpql.append("and upper(r.nome) like :nome ");
      parametros.put("nome", "%" + nome.toUpperCase() + "%");
    }

    if (taxaFreteInicial != null) {
      jpql.append("and r.taxaFrete >= :taxaFreteIncial ");
      parametros.put("taxaFreteIncial", taxaFreteInicial);
    }

    if (taxaFreteFinal != null) {
      jpql.append("and r.taxaFrete <= :taxaFreteFinal ");
      parametros.put("taxaFreteFinal", taxaFreteFinal);
    }

    TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

    parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

    return query.getResultList();
  }
}
