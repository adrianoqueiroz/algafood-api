package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.openapi.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeService cidadeService;

    private static final CidadeModelAssembler cidadeModelAssembler = new CidadeModelAssembler();

    @Override
    @GetMapping("/{id}")
    public CidadeModel buscar(@PathVariable Long id) {
        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidadeService.buscar(id));

        return cidadeModel;
    }

    @Override
    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeService.listar());
    }

    @Override
    @PostMapping
    public CidadeModel adicionar(@RequestBody Cidade cidade) {
        try {
            CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }

    @Override
    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {

        try {
            Cidade cidadeAtual = cidadeService.buscar(id);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            return cidadeService.salvar(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
