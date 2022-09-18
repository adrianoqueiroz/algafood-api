package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
@RequiredArgsConstructor
public class LocalFotoStorageService implements FotoStorageService {

    private final StorageProperties storageProperties;

    @Override
    public InputStream recuperar(String nomeArquivo) {

        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            return Files.newInputStream(arquivoPath);

        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
        Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

        Files.createDirectories(arquivoPath.getParent());
        Files.copy(novaFoto.getInputStream(), arquivoPath);

        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);

            Files.deleteIfExists(arquivoPath);

        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
