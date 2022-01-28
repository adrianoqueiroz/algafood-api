package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {
  List<Restaurante> listar();
  Restaurante buscar(Long id);
  Restaurante salvar(Restaurante restaurante);
  void remover(Restaurante restaurante);
}
