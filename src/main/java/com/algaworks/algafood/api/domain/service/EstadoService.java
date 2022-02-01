package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

  @Autowired
  private EstadoRepository estadoRepository;

  public Estado salvar(Estado estado) {
    return estadoRepository.save(estado);
  }

  public void remover(Long id) {
    try {
      estadoRepository.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
       throw new EntidadeNaoEncontradaException(
           String.format("Não existe estado com código %d", id));

    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
            String.format("Estado de código %d não pode ser removido pois está em uso", id));
    }
  }

  public Optional<Estado> buscar(Long id) {
    return estadoRepository.findById(id);
  }

  public List<Estado> listar() {
    return estadoRepository.findAll();
  }
}