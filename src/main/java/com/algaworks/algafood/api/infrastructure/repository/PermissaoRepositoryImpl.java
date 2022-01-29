package com.algaworks.algafood.api.infrastructure.repository;

import com.algaworks.algafood.api.domain.model.Permissao;
import com.algaworks.algafood.api.domain.repository.PermissaoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<Permissao> listar() {
    return manager.createQuery("from Permissao", Permissao.class).getResultList();
  }

  @Override
  public Permissao buscar(Long id) {
    return manager.find(Permissao.class, id);
  }

  @Override
  @Transactional
  public Permissao salvar(Permissao permissao) {
    return manager.merge(permissao);
  }

  @Override
  @Transactional
  public void remover(Permissao permissao) {
    permissao = buscar(permissao.getId());

    if(permissao == null) {
      throw new EmptyResultDataAccessException(1);
    }

    manager.remove(permissao);
  }

}
