package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Restaurante;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RestauranteModel {
    private final Long id;
    private final String nome;
    private final BigDecimal taxaFrete;
    private final CozinhaModel cozinha;

    public RestauranteModel(Restaurante restaurante) {
        this.id = restaurante.getId();
        this.nome = restaurante.getNome();
        this.taxaFrete = restaurante.getTaxaFrete();
        this.cozinha = new CozinhaModel(restaurante.getCozinha());
    }
}
