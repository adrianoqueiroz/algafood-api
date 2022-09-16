package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogoFotoProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());

        Optional<FotoProduto> fotoExistente = produtoRepository
            .findFotoById(restauranteId, produtoId);

        fotoExistente.ifPresent(produtoRepository::delete);

        FotoProduto fotoSalva = produtoRepository.save(foto);

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
            .nomeArquivo(novoNomeArquivo)
            .inputStream(dadosArquivo)
            .build();

        fotoStorageService.armazenar(novaFoto);

        return fotoSalva;
    }
}
