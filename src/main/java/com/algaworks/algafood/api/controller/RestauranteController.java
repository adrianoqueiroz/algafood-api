package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes")
public class RestauranteController {

  private final RestauranteService restauranteService;

  @GetMapping("/{id}")
  public Restaurante buscar(@PathVariable Long id) {
    return restauranteService.buscar(id);
  }

  @GetMapping
  public List<Restaurante> listar() {
    return restauranteService.listar();
  }

  @PostMapping
  public Restaurante adicionar(@RequestBody Restaurante restaurante) {
      return restauranteService.salvar(restaurante);
  }

  @DeleteMapping("/{id}")
  public void remover(@PathVariable Long id) {
    restauranteService.remover(id);
  }

  @PutMapping("/{id}")
  public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
    Restaurante restauranteAtual = restauranteService.buscar(id);

    BeanUtils.copyProperties(restaurante, restauranteAtual,
        "id", "formasPagamento", "endereco", "createdAt", "produtos");
    return restauranteService.salvar(restauranteAtual);
  }

  @PatchMapping("/{id}")
  public Restaurante atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos) {

    Restaurante restauranteAtual = restauranteService.buscar(id);

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
