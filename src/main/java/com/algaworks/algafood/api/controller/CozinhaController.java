package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

  @Autowired
  private CozinhaService cozinhaService;



  @GetMapping("/{id}")
  public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
    Cozinha cozinha = cozinhaService.buscar(id);

    return cozinha != null ? ResponseEntity.ok(cozinha) : ResponseEntity.notFound().build();
  }

  @GetMapping
  public List<Cozinha> listar() {
    return cozinhaService.listar();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
    Cozinha cozinhaSalva = cozinhaService.salvar(cozinha);

    return cozinhaSalva != null ? ResponseEntity.ok(cozinhaSalva) :
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Cozinha> remover(@PathVariable Long id) {

    try {
      cozinhaService.remover(id);
      return ResponseEntity.noContent().build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
    Cozinha cozinhaAtual = cozinhaService.buscar(id);

    if (cozinhaAtual == null) {
      return ResponseEntity.notFound().build();
    }

    BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
    return ResponseEntity.ok(cozinhaService.salvar(cozinhaAtual));
  }
}
