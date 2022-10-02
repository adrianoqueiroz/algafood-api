package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/estados")
public class EstadoController {

    private final EstadoService estadoService;
    private final EstadoModelAssembler estadoModelAssembler;

    @GetMapping("/{id}")
    public EstadoModel buscar(@PathVariable Long id) {
        return estadoModelAssembler.toModel(estadoService.buscar(id));
    }

    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        return estadoModelAssembler.toCollectionModel(estadoService.listar());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody Estado estado) {
        return estadoModelAssembler.toModel(estadoService.salvar(estado));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        estadoService.remover(id);
    }

    @PutMapping("/{id}")
    public EstadoModel atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        Estado estadoAtual = estadoService.buscar(id);

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return estadoModelAssembler.toModel(estadoService.salvar(estadoAtual));
    }
}
