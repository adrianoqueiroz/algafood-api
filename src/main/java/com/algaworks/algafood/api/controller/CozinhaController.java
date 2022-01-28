package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @GetMapping("/{id}")
  public  Cozinha buscar(@PathVariable Long id) {
    return cozinhaRepository.buscar(id);
  }

  @GetMapping
  public List<Cozinha> listar() {
    return cozinhaRepository.listar();
  }

  @PostMapping
  public Cozinha salvar(@RequestBody Cozinha cozinha) {
    return cozinhaRepository.salvar(cozinha);
  }

  @DeleteMapping("/{id}")
  public void remover(@PathVariable Long id) {
    cozinhaRepository.remover(id);
  }
}
