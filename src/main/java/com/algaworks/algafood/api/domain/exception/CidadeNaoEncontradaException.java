package com.algaworks.algafood.api.domain.exception;

import java.io.Serial;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long cidadeId) {
        this("Não existe uma cidade com código " + cidadeId);
    }
}
