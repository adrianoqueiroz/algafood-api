package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface GrupoControllerOpenApi {
    @GetMapping
    List<GrupoModel> listar();

    @GetMapping("/{grupoId}")
    GrupoModel buscar(@PathVariable Long grupoId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput);

    @PutMapping("/{grupoId}")
    GrupoModel atualizar(@PathVariable Long grupoId,
                         @RequestBody @Valid GrupoInput grupoInput);

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remover(@PathVariable Long grupoId);

    GrupoModel toModel(Grupo grupo);

    List<GrupoModel> toCollectionModel(List<Grupo> grupos);

    Grupo toDomainObject(GrupoInput grupoInput);

    void copyToDomainObject(GrupoInput grupoInput, Grupo grupo);
}
