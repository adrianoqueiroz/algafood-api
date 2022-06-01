package com.algaworks.algafood.api.domain.exception;

import java.io.Serial;

public class EntidadeEmUsoException extends NegocioException{

    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoException(String message) {
        super(message);
    }
}
