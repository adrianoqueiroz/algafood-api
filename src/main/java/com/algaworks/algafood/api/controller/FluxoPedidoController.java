package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}")
@RequiredArgsConstructor
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable UUID codigoPedido) {
        fluxoPedidoService.confirmar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  cancelar(@PathVariable UUID codigoPedido) {
        fluxoPedidoService.cancelar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  entregar(@PathVariable UUID codigoPedido) {
        fluxoPedidoService.entregar(codigoPedido);

        return ResponseEntity.noContent().build();
    }
}
