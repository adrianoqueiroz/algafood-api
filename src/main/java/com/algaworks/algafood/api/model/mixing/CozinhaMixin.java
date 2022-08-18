package com.algaworks.algafood.api.model.mixing;

import com.algaworks.algafood.api.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class CozinhaMixin {
    @JsonIgnore
    List<Restaurante> restaurantes;
}
