package com.algaworks.algafood.core.jackson;

<<<<<<< HEAD:src/main/java/com/algaworks/algafood/core/jackson/JacksonMixinModule.java
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.api.model.mixing.CozinhaMixin;
=======
import com.algaworks.algafood.api.domain.model.Restaurante;
>>>>>>> parent of 2528a1d (Adding mixin classes):src/main/java/com/algaworks/algafood/api/core/jackson/JacksonMixinModule.java
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
