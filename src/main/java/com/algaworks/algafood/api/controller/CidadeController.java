package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cidadeService.buscar(id);
    }

    @GetMapping
    public List<Cidade> listar() {
        return cidadeService.listar();
    }

    @PostMapping
    public Cidade adicionar(@RequestBody Cidade cidade) {
        return cidadeService.salvar(cidade);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cidadeService.buscar(id);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        return cidadeService.salvar(cidadeAtual);
    }
}
