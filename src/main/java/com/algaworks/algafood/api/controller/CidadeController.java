package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

  @Autowired
  private CidadeService cidadeService;



  @GetMapping("/{id}")
  public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
    Cidade cidade = cidadeService.buscar(id);

    return cidade != null ? ResponseEntity.ok(cidade) : ResponseEntity.notFound().build();
  }

  @GetMapping
  public List<Cidade> listar() {
    return cidadeService.listar();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade) {
    Cidade cidadeSalva = cidadeService.salvar(cidade);

    return cidadeSalva != null ? ResponseEntity.ok(cidadeSalva) :
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Cidade> remover(@PathVariable Long id) {

    try {
      cidadeService.remover(id);
      return ResponseEntity.noContent().build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
    Cidade cidadeAtual = cidadeService.buscar(id);

    if (cidadeAtual == null) {
      return ResponseEntity.notFound().build();
    }

    BeanUtils.copyProperties(cidade, cidadeAtual, "id");
    return ResponseEntity.ok(cidadeService.salvar(cidadeAtual));
  }
}
