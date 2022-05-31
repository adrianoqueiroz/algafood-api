package com.algaworks.algafood.api.domain.exception;

import java.io.Serial;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        this("Não existe um estado com código " + estadoId);
    }
}
