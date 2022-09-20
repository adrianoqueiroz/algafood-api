package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacaoClienteListener {

    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        enviarEmailConfirmacao(pedido);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        enviarEmailCancelamento(pedido);
    }

    private void enviarEmailConfirmacao(Pedido pedido) {
        var mensagem = EnvioEmailService.Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
            .corpo("pedido-confirmado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();

        envioEmailService.enviar(mensagem);

        log.info("Notificação enviada para o cliente de código {}.", pedido.getCliente().getId());
    }
    private void enviarEmailCancelamento(Pedido pedido) {
        var mensagem = EnvioEmailService.Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
            .corpo("pedido-cancelado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();

        envioEmailService.enviar(mensagem);

        log.info("Notificação de cancelamento de pedido enviada para o cliente de código {}.", pedido.getCliente().getId());
    }

}
