package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.List;

public interface GrupoPermissaoControllerOpenApi {
    @GetMapping
    List<PermissaoModel> listar(@PathVariable Long grupoId);

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId);

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId);

    PermissaoModel toModel(Permissao permissao);

    List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes);
}
