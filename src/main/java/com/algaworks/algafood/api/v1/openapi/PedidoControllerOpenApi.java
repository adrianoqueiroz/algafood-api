package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.google.common.collect.ImmutableMap;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.UUID;

public interface PedidoControllerOpenApi {
    @GetMapping
    PagedModel<PedidoResumoModel> pesquisar(VendaDiariaFilter.PedidoFilter filtro, Pageable pageable);

    @GetMapping("/{codigoPedido}")
    PedidoModel buscar(@PathVariable UUID codigoPedido);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput);

    default Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
            "codigo ", "codigo",
            "restaurante.nome", "restaurante.nome",
            "nomeCliente", "cliente.nome",
            "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

    Pedido toDomainObject(PedidoInput pedidoInput);
}
