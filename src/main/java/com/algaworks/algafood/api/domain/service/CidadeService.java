package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    public static final String CIDADE_EM_USO = "Cidade de código %d não pode ser removida pois está em uso";

    private final CidadeRepository cidadeRepository;

    private final EstadoService estadoService;

    @Transactional
    public Cidade salvar(Cidade cidade) {
            Estado estado = estadoService.buscar(cidade.getEstado().getId());

            cidade.setEstado(estado);
            return cidadeRepository.save(cidade);
    }

    @Transactional
    public void remover(Long id) {
        try {
            cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(CIDADE_EM_USO, id));
        }
    }

    public Cidade buscar(Long id) {
        return cidadeRepository.findById(id).orElseThrow(
            () -> new CidadeNaoEncontradaException(id)
        );
    }

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }
}
