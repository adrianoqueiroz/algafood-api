package com.algaworks.algafood.api.core.jackson;

import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.model.mixing.RestauranteMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
