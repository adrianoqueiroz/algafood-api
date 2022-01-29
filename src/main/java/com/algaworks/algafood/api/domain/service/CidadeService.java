package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

  @Autowired
  private CidadeRepository cidadeRepository;

  public Cidade salvar(Cidade cidade) {
    return cidadeRepository.salvar(cidade);
  }

  public void remover(Long id) {
    try {
      cidadeRepository.remover(id);

    } catch (EmptyResultDataAccessException e) {
       throw new EntidadeNaoEncontradaException(
           String.format("Não existe cidade com código %d", id));

    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
            String.format("Cidade de código %d não pode ser removida pois está em uso", id));
    }
  }

  public Cidade buscar(Long id) {
    return cidadeRepository.buscar(id);
  }

  public List<Cidade> listar() {
    return cidadeRepository.listar();
  }
}
