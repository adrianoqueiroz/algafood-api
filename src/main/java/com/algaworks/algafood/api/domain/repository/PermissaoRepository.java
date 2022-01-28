package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

  List<Permissao> listar();
  Permissao buscar(Long id);
  Permissao salvar(Permissao permissao);
  void remover(Permissao permissao);
}
