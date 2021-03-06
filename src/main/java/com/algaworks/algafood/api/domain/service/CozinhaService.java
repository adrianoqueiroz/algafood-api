package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CozinhaService {

  public static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida pois está em uso";

  private final CozinhaRepository cozinhaRepository;

  public Cozinha salvar(Cozinha cozinha) {
    return cozinhaRepository.save(cozinha);
  }

  public void remover(Long id) {
    try {
      cozinhaRepository.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
       throw new CozinhaNaoEncontradaException(id);
    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO, id));
    }
  }

  public Cozinha buscar(Long id) {
    return cozinhaRepository.findById(id)
        .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
  }

  public List<Cozinha> listar() {
    return cozinhaRepository.findAll();
  }
}
