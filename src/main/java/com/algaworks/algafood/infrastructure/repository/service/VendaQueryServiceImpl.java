package com.algaworks.algafood.infrastructure.repository.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(VendaDiaria.class);

        var functionDateCreatedAt = builder.function("TO_CHAR", String.class,
            root.get("createdAt"),
            builder.literal("yyyy-MM-dd"));

        var selection = builder.construct(VendaDiaria.class,
                functionDateCreatedAt,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateCreatedAt);

        return manager.createQuery(query).getResultList();
    }
}
