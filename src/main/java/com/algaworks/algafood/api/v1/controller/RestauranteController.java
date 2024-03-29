package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.listener.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurantes")
public class RestauranteController implements com.algaworks.algafood.api.v1.openapi.RestauranteControllerOpenApi {

    private static final ModelMapper modelMapper = new ModelMapper();
    private final RestauranteService restauranteService;
    private final SmartValidator validator;


    private final RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    private final RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

    @Override
    @GetMapping("/{id}")
    public RestauranteModel buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);
        return new RestauranteModel(restaurante);
    }

//    @JsonView(RestauranteView.Resumo.class)
    @Override
    @GetMapping

    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicoModelAssembler
            .toCollectionModel(restauranteService.listar());
    }

//    @JsonView(RestauranteView.ApenasNome.class)
    @Override
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeModelAssembler
            .toCollectionModel(restauranteService.listar());
    }

    @Override
    @PostMapping
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            Restaurante restaurante = restauranteInput.toDomainObject(restauranteInput);

            return new RestauranteModel(restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        restauranteService.remover(id);
    }

    @Override
    @PutMapping("/{id}")
    public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            Restaurante restauranteAtual = restauranteService.buscar(id);

            restauranteAtual.setCozinha(new Cozinha());

            if(restauranteAtual.getEndereco().getCidade().getId() != null) {
                restauranteAtual.getEndereco().setCidade(new Cidade());
            }

            modelMapper.map(restauranteInput, restauranteAtual);
            return new RestauranteModel(restauranteService.salvar(restauranteAtual));
        } catch (CidadeNaoEncontradaException | CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @PatchMapping("/{id}")
    public RestauranteModel atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {

        Restaurante restauranteAtual = restauranteService.buscar(id);

        merge(campos, restauranteAtual, request);

        validate(restauranteAtual, "restaurante");

        return atualizar(id, new RestauranteInput(restauranteAtual));
    }

    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> ids) {
        try {
            restauranteService.ativar(ids);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> ids) {
        try {
            restauranteService.inativar(ids);
        } catch (RestauranteNaoEncontradoException  e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge( Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = mapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, key);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @Override
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);

        return ResponseEntity.noContent().build();
    }
}
