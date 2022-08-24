package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

  public static final String ESTADO_EM_USO = "Estado de código %d não pode ser removido pois está em uso";

  private final EstadoRepository estadoRepository;

  @Transactional
  public Estado salvar(Estado estado) {
    return estadoRepository.save(estado);
  }

  @Transactional
  public void remover(Long id) {
    try {
      estadoRepository.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
       throw new EstadoNaoEncontradoException(id);

    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
            String.format(ESTADO_EM_USO, id));
    }
  }

  public Estado buscar(Long id) {
    return estadoRepository.findById(id)
        .orElseThrow(() -> new EstadoNaoEncontradoException(id));
  }

  public List<Estado> listar() {
    return estadoRepository.findAll();
  }
}
