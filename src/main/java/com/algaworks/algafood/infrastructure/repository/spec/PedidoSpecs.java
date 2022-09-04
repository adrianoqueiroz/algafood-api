package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

  public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
    return (root, query, criteriaBuilder) -> {
        var predicates = new ArrayList<Predicate>();

        root.fetch("restaurante").fetch("cozinha");
        root.fetch("cliente");

        if(filtro.getClienteId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("cliente"), filtro.getClienteId()));
        }

        if(filtro.getRestauranteId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
        }

        if(filtro.getBeginCreatedAt() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filtro.getBeginCreatedAt()));
        }

        if(filtro.getEndCreatedAt() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filtro.getBeginCreatedAt()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
