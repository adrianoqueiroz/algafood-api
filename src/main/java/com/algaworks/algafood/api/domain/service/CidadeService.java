package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private EstadoService estadoService;

  public Cidade salvar(Cidade cidade) {
    try {
      Optional<Estado> estado = estadoService.buscar(cidade.getEstado().getId());

      if(estado.isEmpty()) {
        throw new EntidadeNaoEncontradaException(
            String.format("Não existe estado com código %d", cidade.getEstado().getId()));
      }
      cidade.setEstado(estado.get());
      return cidadeRepository.save(cidade);

    } catch (EmptyResultDataAccessException e) {
      throw new EntidadeNaoEncontradaException(
          String.format("Cidade de código %d não pode ser encontrada", cidade.getId()));
    }
  }

  public void remover(Long id) {
    try {
      cidadeRepository.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
       throw new EntidadeNaoEncontradaException(
           String.format("Não existe cidade com código %d", id));

    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
            String.format("Cidade de código %d não pode ser removida pois está em uso", id));
    }
  }

  public Optional<Cidade> buscar(Long id) {
    return cidadeRepository.findById(id);
  }

  public List<Cidade> listar() {
    return cidadeRepository.findAll();
  }
}
