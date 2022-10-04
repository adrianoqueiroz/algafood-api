package com.algaworks.algafood.api.v1.openapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public interface FluxoPedidoControllerOpenApi {
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> confirmar(@PathVariable UUID codigoPedido);

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> cancelar(@PathVariable UUID codigoPedido);

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> entregar(@PathVariable UUID codigoPedido);
}
