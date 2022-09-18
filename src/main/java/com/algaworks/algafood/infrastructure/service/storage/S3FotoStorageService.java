package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3FotoStorageService implements FotoStorageService {

    private final AmazonS3 amazonS3;
    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String nomeArquivo) {

    }
}