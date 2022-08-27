package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    public static final String RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso";

    private final RestauranteRepository restauranteRepository;

    private final CozinhaService cozinhaService;
    private final CidadeService cidadeService;

    private final FormaPagamentoService formaPagamentoService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscar(cozinhaId);
        Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void remover(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(RESTAURANTE_EM_USO, id));
        }
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.inativar();
    }

    public Restaurante buscar(Long id) {
        return restauranteRepository.findById(id).orElseThrow(
            () -> new RestauranteNaoEncontradoException(id)
        );
    }

    @Transactional
    public boolean desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);

        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        return restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public boolean associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);

        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        return restaurante.adicionarFormaPagamento(formaPagamento);
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }
}
