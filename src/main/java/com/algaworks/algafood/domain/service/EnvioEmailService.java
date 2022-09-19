package com.algaworks.algafood.domain.service;


import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

public interface EnvioEmailService {

        void enviar(Mensagem mensagem);

        @Builder
        @Getter
        class Mensagem {
            @Singular
            private List<String> destinatarios;

            @NotBlank
            private String assunto;

            @NotBlank
            private String corpo;

            @Singular("variavel")
            private Map<String, Object> variaveis;
        }
}
