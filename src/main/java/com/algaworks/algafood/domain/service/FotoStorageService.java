package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {
    InputStream recuperar(String nomeArquivo);
    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoExistente != null) {
            this.remover(nomeArquivoExistente);
        }
    }

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }
}
