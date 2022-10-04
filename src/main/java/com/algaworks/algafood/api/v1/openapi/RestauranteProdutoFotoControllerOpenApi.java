package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public interface RestauranteProdutoFotoControllerOpenApi {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    FotoProdutoModel buscar(@PathVariable Long restauranteId,
                            @PathVariable Long produtoId);

    @GetMapping
    ResponseEntity<?> buscarArquivoFoto(
        @PathVariable Long restauranteId,
        @PathVariable Long produtoId,
        @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                   @PathVariable Long produtoId,
                                   @Valid FotoProdutoInput fotoProdutoInput) throws IOException;

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    void excluir(@PathVariable Long restauranteId,
                 @PathVariable Long produtoId);

    FotoProdutoModel toModel(FotoProduto foto);
}
