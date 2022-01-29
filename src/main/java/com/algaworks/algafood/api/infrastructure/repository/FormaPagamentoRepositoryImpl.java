package com.algaworks.algafood.api.infrastructure.repository;

import com.algaworks.algafood.api.domain.model.FormaPagamento;
import com.algaworks.algafood.api.domain.repository.FormaPagamentoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<FormaPagamento> listar() {
    return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
  }

  @Override
  public FormaPagamento buscar(Long id) {
    return manager.find(FormaPagamento.class, id);
  }

  @Override
  @Transactional
  public FormaPagamento salvar(FormaPagamento formaPagamento) {
    return manager.merge(formaPagamento);
  }

  @Override
  @Transactional
  public void remover(FormaPagamento formaPagamento) {
    formaPagamento = buscar(formaPagamento.getId());

    if(formaPagamento == null) {
      throw new EmptyResultDataAccessException(1);
    }

    manager.remove(formaPagamento);
  }

}
