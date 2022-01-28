package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

  List<Cidade> listar();
  Cidade buscar(Long id);
  Cidade salvar(Cidade cidade);
  void remover(Cidade cidade);
}
