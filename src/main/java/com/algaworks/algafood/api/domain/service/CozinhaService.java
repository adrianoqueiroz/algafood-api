package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CozinhaService {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  public Cozinha salvar(Cozinha cozinha) {
    return cozinhaRepository.save(cozinha);
  }

  public void remover(Long id) {
    try {
      cozinhaRepository.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
       throw new EntidadeNaoEncontradaException(
           String.format("Não existe cozinha com código %d", id));

    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
            String.format("Cozinha de código %d não pode ser removida pois está em uso", id));
    }
  }

  public Optional<Cozinha> buscar(Long id) {
    return cozinhaRepository.findById(id);
  }

  public List<Cozinha> listar() {
    return cozinhaRepository.findAll();
  }
}
