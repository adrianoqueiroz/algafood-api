package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface RestauranteControllerOpenApi {
    @GetMapping("/{id}")
    RestauranteModel buscar(@PathVariable Long id);

    //    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    CollectionModel<RestauranteBasicoModel> listar();

    //    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    @PostMapping
    RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput);

    @DeleteMapping("/{id}")
    void remover(@PathVariable Long id);

    @PutMapping("/{id}")
    RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput);

    @PatchMapping("/{id}")
    RestauranteModel atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request);

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void ativarMultiplos(@RequestBody List<Long> ids);

    @DeleteMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void inativarMultiplos(@RequestBody List<Long> ids);

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> abrir(@PathVariable Long restauranteId);

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> fechar(@PathVariable Long restauranteId);

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> ativar(@PathVariable Long restauranteId);

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> inativar(@PathVariable Long restauranteId);
}
