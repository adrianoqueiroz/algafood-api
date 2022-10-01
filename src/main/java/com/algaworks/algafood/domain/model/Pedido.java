package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_id_generator")
    @SequenceGenerator(name = "pedido_id_generator", sequenceName = "pedido_id_seq", allocationSize = 1)
    private Long id;
    private UUID codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "timestamp")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;
    private OffsetDateTime confirmedAt;
    private OffsetDateTime cancelledAt;
    private OffsetDateTime deliveredAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
            .map(ItemPedido::getPrecoTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public boolean podeSerConfirmado() {
        return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
    }

    public boolean podeSerEntregue() {
        return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
    }

    public boolean podeSerCancelado() {
        return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
    }

    public void definirFrete() {
        setTaxaFrete(getRestaurante().getTaxaFrete());
    }

    public void atribuirPedidoAosItens() {
        getItens().forEach(item -> item.setPedido(this));
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigo(UUID.randomUUID());
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setConfirmedAt(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDeliveredAt(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setCancelledAt(OffsetDateTime.now());

        registerEvent(new PedidoCanceladoEvent(this));
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s",
                getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pedido pedido = (Pedido) o;
        return id != null && Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
