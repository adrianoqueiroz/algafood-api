package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(UUID codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscar(codigoPedido);
        pedido.confirmar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(UUID pedidoId) {
        Pedido pedido = emissaoPedidoService.buscar(pedidoId);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(UUID pedidoId) {
        Pedido pedido = emissaoPedidoService.buscar(pedidoId);
        pedido.entregar();
    }
}
