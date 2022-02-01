package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

  @Autowired
  private EstadoService estadoService;



  @GetMapping("/{id}")
  public ResponseEntity<Estado> buscar(@PathVariable Long id) {
    Optional<Estado> estado = estadoService.buscar(id);

    return estado.isPresent() ? ResponseEntity.ok(estado.get()) : ResponseEntity.notFound().build();
  }

  @GetMapping
  public List<Estado> listar() {
    return estadoService.listar();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
    Estado estadoSalvo = estadoService.salvar(estado);

    return estadoSalvo != null ? ResponseEntity.ok(estadoSalvo) :
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Estado> remover(@PathVariable Long id) {

    try {
      estadoService.remover(id);
      return ResponseEntity.noContent().build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
    Optional<Estado> estadoAtual = estadoService.buscar(id);

    if (estadoAtual.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
    return ResponseEntity.ok(estadoService.salvar(estadoAtual.get()));
  }
}
