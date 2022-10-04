package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements com.algaworks.algafood.api.v1.openapi.RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private RestauranteService cadastroRestaurante;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    @GetMapping
    public List<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

        return toCollectionModel(restaurante.getResponsaveis());
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
    }

    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
    }

    @Override
    public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
        return usuarios.stream()
            .map(usuario -> toModel(usuario))
            .collect(Collectors.toList());
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }
}