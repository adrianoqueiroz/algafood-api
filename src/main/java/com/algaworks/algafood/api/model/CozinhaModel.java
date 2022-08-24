package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Cozinha;
import lombok.Getter;

@Getter
public class CozinhaModel {
    private final Long id;
    private final String nome;

    public CozinhaModel(Cozinha cozinha) {
        this.id = cozinha.getId();
        this.nome = cozinha.getNome();
    }
}
