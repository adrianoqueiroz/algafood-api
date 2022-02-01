package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CozinhaRepository cozinhaRepository;

  public Restaurante salvar(Restaurante restaurante) {
    Long cozinhaId = restaurante.getCozinha().getId();
    Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
        .orElseThrow(() -> new EntidadeNaoEncontradaException(
            String.format("Cozinha de código %d não encontrada", cozinhaId)));

    restaurante.setCozinha(cozinha);

    return restauranteRepository.save(restaurante);
  }

  public void remover(Long id) {
    try {
      restauranteRepository.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
       throw new EntidadeNaoEncontradaException(
           String.format("Não existe restaurante com código %d", id));

    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
            String.format("Restaurante de código %d não pode ser removido, pois está em uso", id));
    }
  }

  public Optional<Restaurante> buscar(Long id) {
    return restauranteRepository.findById(id);
  }

  public List<Restaurante> listar() {
    return restauranteRepository.findAll();
  }
}
