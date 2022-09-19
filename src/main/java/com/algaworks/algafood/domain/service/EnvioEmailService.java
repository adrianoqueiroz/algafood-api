package com.algaworks.algafood.domain.service;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

public interface EnvioEmailService {

        void enviar(Mensagem mensagem);

        @Builder
        @Getter
        class Mensagem {

            private List<String> destinatarios;
            private String assunto;
            private String corpo;
        }
}
