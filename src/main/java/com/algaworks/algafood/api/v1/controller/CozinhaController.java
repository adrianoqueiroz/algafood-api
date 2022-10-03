package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cozinhas")
public class CozinhaController {

    private final CozinhaService cozinhaService;
    private final CozinhaModelAssembler cozinhaModelAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping("/{id}")
    public CozinhaModel buscar(@PathVariable Long id) {
        return cozinhaModelAssembler.toModel(cozinhaService.buscar(id));
    }

    @GetMapping
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        log.info("Consultando cozinhas com paginação de {} registros", pageable.getPageSize());

        if(true) {
            throw new RuntimeException("Erro de teste");
        }

        Page<Cozinha> cozinhasPage = cozinhaService.listar(pageable);

        return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid Cozinha cozinha) {
        return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinha));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.remover(id);
    }

    @PutMapping("/{id}")
    public CozinhaModel atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaService.buscar(id);

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinhaAtual));
    }
}
