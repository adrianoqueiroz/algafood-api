package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(UUID codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscar(codigoPedido);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(UUID pedidoId) {
        Pedido pedido = emissaoPedidoService.buscar(pedidoId);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(UUID pedidoId) {
        Pedido pedido = emissaoPedidoService.buscar(pedidoId);
        pedido.entregar();
    }
}
