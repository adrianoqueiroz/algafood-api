package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements com.algaworks.algafood.api.v1.openapi.RestauranteProdutoFotoControllerOpenApi {

    private final CatalogoFotoProdutoService catalogoFotoProdutoService;
    private final ProdutoService produtoService;
    private final FotoStorageService fotoStorageService;
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscar(@PathVariable Long restauranteId,
                                   @PathVariable Long produtoId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);

        return toModel(fotoProduto);
    }

    @Override
    @GetMapping
    public ResponseEntity<?> buscarArquivoFoto(
        @PathVariable Long restauranteId,
        @PathVariable Long produtoId,
        @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);
            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            MediaType fotoMediaType = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            if (!isCompatible(fotoMediaType, mediaTypesAceitas)) {
                throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
            }

            if(fotoRecuperada.temUrl()) {
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                    .build();
            } else {
                return ResponseEntity.ok()
                    .contentType(fotoMediaType)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }


        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                          @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());

        return toModel(fotoSalva);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId) {
        catalogoFotoProdutoService.excluir(restauranteId, produtoId);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoModel.class);
    }

    private boolean isCompatible(MediaType mediaType, List<MediaType> mediaTypesAceitas) {
        return mediaTypesAceitas.stream()
            .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaType));
    }


}
