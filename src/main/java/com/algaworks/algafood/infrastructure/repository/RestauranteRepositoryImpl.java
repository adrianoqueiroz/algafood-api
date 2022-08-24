package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

  @PersistenceContext
  private EntityManager manager;

  @Autowired
  @Lazy
  private RestauranteRepository restauranteRepository;

  @Override
  public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
    CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

    CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
    Root<Restaurante> rootRestaurante = criteriaQuery.from(Restaurante.class); //from Restaurante

    var predicates = new ArrayList<Predicate>();

    if(StringUtils.hasLength(nome)){
      predicates.add(criteriaBuilder.like(rootRestaurante.get("nome"), "%" + nome + "%"));
    }

    if(taxaFreteInicial != null){
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootRestaurante.get("taxaFrete"), taxaFreteInicial));
    }

    if(taxaFreteFinal != null){
      predicates.add(criteriaBuilder.lessThanOrEqualTo(rootRestaurante.get("taxaFrete"), taxaFreteFinal));
    }

    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    TypedQuery<Restaurante> query = manager.createQuery(criteriaQuery);

    return query.getResultList();
  }

  @Override
  public List<Restaurante> findComFreteGratis(String nome) {
    return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
  }

}
