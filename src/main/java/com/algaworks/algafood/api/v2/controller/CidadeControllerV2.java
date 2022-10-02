package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
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
@RequestMapping(value = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    private final CidadeService cidadeService;

    private static final CidadeModelAssemblerV2 cidadeModelAssembler = new CidadeModelAssemblerV2();

    @GetMapping("/{id}")
    public CidadeModelV2 buscar(@PathVariable Long id) {
        CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidadeService.buscar(id));

        return cidadeModel;
    }

    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeService.listar());
    }

    @PostMapping
    public CidadeModelV2 adicionar(@RequestBody Cidade cidade) {
        try {
            CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }

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
