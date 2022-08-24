package com.algaworks.algafood.domain.exception;

import java.io.Serial;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {
        this("Não existe um restaurante com código " + restauranteId);
    }
}
