package com.algaworks.algafood.api.domain.service;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    public static final String RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso";

    private final RestauranteRepository restauranteRepository;

    private final CozinhaService cozinhaService;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public void remover(Long id) {
        try {
            restauranteRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(RESTAURANTE_EM_USO, id));
        }
    }

    public Restaurante buscar(Long id) {
        return restauranteRepository.findById(id).orElseThrow(
            () -> new RestauranteNaoEncontradoException(id)
        );
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }
}
