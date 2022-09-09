package com.algaworks.algafood.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Order;
import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
        var orders = pageable.getSort().stream()
            .filter(order -> fieldsMapping.containsKey(order.getProperty()))
            .map(order -> {
                var oldProperty = order.getProperty();
                var newProperty = fieldsMapping.get(oldProperty);

                return new Sort.Order(order.getDirection(), newProperty);
            })
            .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
