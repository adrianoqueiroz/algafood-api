package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Set;

public interface RestauranteFormaPagamentoControllerOpenApi {
    @GetMapping
    CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId);

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId);

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId);

    FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput);

    void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento);

    FormaPagamentoModel toModel(FormaPagamento formaPagamento);

    List<FormaPagamentoModel> toCollectionModel(Set<FormaPagamento> formasPagamentos);
}
