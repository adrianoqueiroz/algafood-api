package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.List;

public interface RestauranteUsuarioResponsavelControllerOpenApi {
    @GetMapping
    List<UsuarioModel> listar(@PathVariable Long restauranteId);

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId);

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId);

    List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios);

    UsuarioModel toModel(Usuario usuario);
}
