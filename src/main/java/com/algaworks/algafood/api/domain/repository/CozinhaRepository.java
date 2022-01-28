package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

  List<Cozinha> listar();
  Cozinha buscar(Long id);
  Cozinha salvar(Cozinha cozinha);
  void remover(Long id);
}
