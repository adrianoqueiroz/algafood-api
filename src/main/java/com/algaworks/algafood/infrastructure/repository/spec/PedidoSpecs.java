package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

  public static Specification<Pedido> usandoFiltro(VendaDiariaFilter.PedidoFilter filtro) {
    return (root, query, criteriaBuilder) -> {
        var predicates = new ArrayList<Predicate>();

        if (Pedido.class.equals(query.getResultType())) {
            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");
        }

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
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filtro.getEndCreatedAt()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
