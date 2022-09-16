package com.algaworks.algafood.infrastructure.service.query;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(VendaDiaria.class);

//        var predicates = new ArrayList<Predicate>();
//
//        if (filter.getRestauranteId() != null) {
//            predicates.add((Predicate) builder.equal(root.get("restaurante"), filter.getRestauranteId()));
//        }
//
//        if (filter.getBeginCreatedAt() != null) {
//            predicates.add((Predicate) builder.greaterThanOrEqualTo(root.get("dataCriacao"),
//                filter.getBeginCreatedAt()));
//        }
//
//        if (filter.getEndCreatedAt() != null) {
//            predicates.add((Predicate) builder.lessThanOrEqualTo(root.get("dataCriacao"),
//                filter.getEndCreatedAt()));
//        }
//
//        predicates.add((Predicate) root.get("status").in(
//            StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

//        var functionDateCreatedAt = builder.function("TO_CHAR", String.class,
//            root.get("createdAt"),
//            builder.literal("yyyy-MM-dd"));

        var selection = builder.construct(VendaDiaria.class,
//                functionDateCreatedAt,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
//        query.where(predicates.toArray(new Predicate[0]));
//        query.groupBy(functionDateCreatedAt);

        return manager.createQuery(query).getResultList();
    }
}
