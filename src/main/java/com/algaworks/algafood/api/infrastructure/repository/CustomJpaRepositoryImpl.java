package com.algaworks.algafood.api.infrastructure.repository;

import com.algaworks.algafood.api.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

  private EntityManager entityManager;

  public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
      super(entityInformation, entityManager);
      this.entityManager = entityManager;
  }


  @Override
  public Optional<T> buscarPrimeiro() {
    var jpql = "select e from " + getDomainClass().getName() + " e where e.id = :id";

    T entity = entityManager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
    return Optional.ofNullable(entity);
  }
}
