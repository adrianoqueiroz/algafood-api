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
    private final EnvioEmailService envioEmailService;

    @Transactional
    public void confirmar(UUID codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscar(codigoPedido);
        pedido.confirmar();

        var mensagem = EnvioEmailService.Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
            .corpo("pedido-confirmado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();

        envioEmailService.enviar(mensagem);
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
