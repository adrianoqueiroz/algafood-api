package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoService {

  public static final String ESTADO_NAO_ENCONTRADO = "Não existe estado com código %d";
  public static final String ESTADO_EM_USO = "Estado de código %d não pode ser removido pois está em uso";

  private final EstadoRepository estadoRepository;

  public Estado salvar(Estado estado) {
    return estadoRepository.save(estado);
  }

  public void remover(Long id) {
    try {
      estadoRepository.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
       throw new EntidadeNaoEncontradaException(
           String.format(ESTADO_NAO_ENCONTRADO, id));

    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
            String.format(ESTADO_EM_USO, id));
    }
  }

  public Estado buscar(Long id) {
    return estadoRepository.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradaException(ESTADO_NAO_ENCONTRADO));
  }

  public List<Estado> listar() {
    return estadoRepository.findAll();
  }
}
