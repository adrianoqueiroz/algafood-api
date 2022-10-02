package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.v1.model.mixing.CozinhaMixin;
import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
