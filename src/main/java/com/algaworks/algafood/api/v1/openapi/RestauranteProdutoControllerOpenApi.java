package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface RestauranteProdutoControllerOpenApi {
    @GetMapping
    List<ProdutoModel> listar(@PathVariable Long restauranteId,
                              @RequestParam(required = false) boolean incluirInativos);

    @GetMapping("/{produtoId}")
    ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProdutoModel adicionar(@PathVariable Long restauranteId,
                           @RequestBody @Valid ProdutoInput produtoInput);

    @PutMapping("/{produtoId}")
    ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                           @RequestBody @Valid ProdutoInput produtoInput);

    ProdutoModel toModel(Produto produto);

    List<ProdutoModel> toCollectionModel(List<Produto> produtos);

    Produto toDomainObject(ProdutoInput produtoInput);

    void copyToDomainObject(ProdutoInput produtoInput, Produto produto);
}
