package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface CozinhaControllerOpenApi {
    @GetMapping("/{id}")
    CozinhaModel buscar(@PathVariable Long id);

    @GetMapping
    PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CozinhaModel adicionar(@RequestBody @Valid Cozinha cozinha);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remover(@PathVariable Long id);

    @PutMapping("/{id}")
    CozinhaModel atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha);
}
