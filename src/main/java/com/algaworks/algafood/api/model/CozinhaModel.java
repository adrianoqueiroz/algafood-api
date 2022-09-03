package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;

@Getter
public class CozinhaModel {
    @JsonView(RestauranteView.Resumo.class)
    private final Long id;
    @JsonView(RestauranteView.Resumo.class)
    private final String nome;

    public CozinhaModel(Cozinha cozinha) {
        this.id = cozinha.getId();
        this.nome = cozinha.getNome();
    }
}
