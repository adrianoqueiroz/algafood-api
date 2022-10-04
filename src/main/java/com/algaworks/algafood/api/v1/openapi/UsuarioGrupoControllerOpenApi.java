package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.List;

public interface UsuarioGrupoControllerOpenApi {
    @GetMapping
    List<GrupoModel> listar(@PathVariable Long usuarioId);

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId);

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId);

    List<GrupoModel> toCollectionModel(Collection<Grupo> grupos);

    GrupoModel toModel(Grupo grupo);
}
