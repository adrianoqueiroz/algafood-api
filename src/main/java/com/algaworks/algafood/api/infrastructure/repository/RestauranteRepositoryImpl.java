package com.algaworks.algafood.api.infrastructure.repository;

import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import com.algaworks.algafood.api.domain.model.Restaurante;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<Restaurante> listar() {
    return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
  }

  @Override
  public Restaurante buscar(Long id) {
    return manager.find(Restaurante.class, id);
  }

  @Override
  @Transactional
  public Restaurante salvar(Restaurante restaurante) {
    return manager.merge(restaurante);
  }

  @Override
  @Transactional
  public void remover(Restaurante restaurante) {
    restaurante = buscar(restaurante.getId());
    manager.remove(restaurante);
  }

}
