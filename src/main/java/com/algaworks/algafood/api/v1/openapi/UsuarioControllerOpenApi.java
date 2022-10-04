package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface UsuarioControllerOpenApi {
    @GetMapping
    CollectionModel<UsuarioModel> listar();

    @GetMapping("/{usuarioId}")
    UsuarioModel buscar(@PathVariable Long usuarioId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput);

    @PutMapping("/{usuarioId}")
    UsuarioModel atualizar(@PathVariable Long usuarioId,
                           @RequestBody @Valid UsuarioInput usuarioInput);

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha);

    Usuario toDomainObject(UsuarioInput usuarioInput);

    void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario);
}
