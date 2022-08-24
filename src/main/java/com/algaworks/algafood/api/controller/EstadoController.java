package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
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

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

  @Autowired
  private EstadoService estadoService;



  @GetMapping("/{id}")
  public Estado buscar(@PathVariable Long id) {
    return estadoService.buscar(id);
  }

  @GetMapping
  public List<Estado> listar() {
    return estadoService.listar();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Estado adicionar(@RequestBody Estado estado) {
    return estadoService.salvar(estado);
  }

  @DeleteMapping("/{id}")
  public void remover(@PathVariable Long id) {
      estadoService.remover(id);
  }

  @PutMapping("/{id}")
  public Estado atualizar(@PathVariable Long id, @RequestBody Estado estado) {
    Estado estadoAtual = estadoService.buscar(id);

    BeanUtils.copyProperties(estado, estadoAtual, "id");
    return estadoService.salvar(estadoAtual);
  }
}
