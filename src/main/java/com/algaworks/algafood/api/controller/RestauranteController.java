package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.reflect.ReflectionShadow;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

  @Autowired
  private RestauranteService restauranteService;



  @GetMapping("/{id}")
  public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
    Restaurante restaurante = restauranteService.buscar(id);

    return restaurante != null ? ResponseEntity.ok(restaurante) : ResponseEntity.notFound().build();
  }

  @GetMapping
  public List<Restaurante> listar() {
    return restauranteService.listar();
  }

  @PostMapping
  public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
    try {
      restaurante = restauranteService.salvar(restaurante);
      return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Restaurante> remover(@PathVariable Long id) {

    try {
      restauranteService.remover(id);
      return ResponseEntity.noContent().build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
    Restaurante restauranteAtual = restauranteService.buscar(id);

    try {
      if (restauranteAtual == null) {
        String message = String.format("Restaurante com código %d não encontrado", id);
        throw new EntidadeNaoEncontradaException(message);
      }

      BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
      restaurante = restauranteService.salvar(restauranteAtual);
      return ResponseEntity.ok(restaurante);

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos) {

    Restaurante restauranteAtual = restauranteService.buscar(id);

    if (restauranteAtual == null) {
      String message = String.format("Restaurante com código %d não encontrado", id);
      throw new EntidadeNaoEncontradaException(message);
    }

    merge(campos, restauranteAtual);

    return atualizar(id, restauranteAtual);

  }

  private void merge( Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {

    ObjectMapper mapper = new ObjectMapper();
    Restaurante restauranteOrigem = mapper.convertValue(dadosOrigem, Restaurante.class);

    dadosOrigem.forEach((key, value) -> {
      Field field = ReflectionUtils.findField(Restaurante.class, key);
      field.setAccessible(true);

      Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
      ReflectionUtils.setField(field, restauranteDestino, novoValor);
    });

  }
}
