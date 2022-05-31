package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    public static final String CIDADE_NAO_ENCONTRADA = "Cidade de código %d não encontrada";
    public static final String CIDADE_EM_USO = "Cidade de código %d não pode ser removida pois está em uso";

    private final CidadeRepository cidadeRepository;

    private final EstadoService estadoService;

    public Cidade salvar(Cidade cidade) {
            Estado estado = estadoService.buscar(cidade.getEstado().getId());

            cidade.setEstado(estado);
            return cidadeRepository.save(cidade);
    }

    public void remover(Long id) {
        try {
            cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, id));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(CIDADE_EM_USO, id));
        }
    }

    public Cidade buscar(Long id) {
        return cidadeRepository.findById(id).orElseThrow(
            () -> new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, id))
        );
    }

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }
}
