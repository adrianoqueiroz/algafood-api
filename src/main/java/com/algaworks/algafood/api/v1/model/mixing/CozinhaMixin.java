package com.algaworks.algafood.api.v1.model.mixing;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class CozinhaMixin {
    @JsonIgnore
    List<Restaurante> restaurantes;
}
