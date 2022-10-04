package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;

public interface FormaPagamentoControllerOpenApi {
    @GetMapping
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @GetMapping("/{formaPagamentoId}")
    ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput);

    @PutMapping("/{formaPagamentoId}")
    FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
                                  @RequestBody @Valid FormaPagamentoInput formaPagamentoInput);

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remover(@PathVariable Long formaPagamentoId);

    FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput);

    void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento);
}
