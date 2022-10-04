package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.GrupoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/grupos")
public class GrupoController implements com.algaworks.algafood.api.v1.openapi.GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    @GetMapping
    public List<GrupoModel> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();

        return toCollectionModel(todosGrupos);
    }

    @Override
    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        return toModel(grupo);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = toDomainObject(grupoInput);

        grupo = grupoService.salvar(grupo);

        return toModel(grupo);
    }

    @Override
    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId,
                                @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);

        copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = grupoService.salvar(grupoAtual);

        return toModel(grupoAtual);
    }

    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    @Override
    public List<GrupoModel> toCollectionModel(List<Grupo> grupos) {
        return grupos.stream()
            .map(grupo -> toModel(grupo))
            .collect(Collectors.toList());
    }

    @Override
    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    @Override
    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}