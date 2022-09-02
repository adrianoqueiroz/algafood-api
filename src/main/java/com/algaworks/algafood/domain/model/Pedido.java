package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Pedido {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_id_generator")
  @SequenceGenerator(name = "pedido_id_generator", sequenceName = "pedido_id_seq", allocationSize = 1)
  private Long id;

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
  private List<ItemPedido> itens = new ArrayList<>();

  public void calcularValorTotal() {
    getItens().forEach(ItemPedido::calcularPrecoTotal);

    this.subtotal = getItens().stream()
        .map(ItemPedido::getPrecoTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    this.valorTotal = this.subtotal.add(this.taxaFrete);
  }

  public void definirFrete() {
    setTaxaFrete(getRestaurante().getTaxaFrete());
  }

  public void atribuirPedidoAosItens() {
    getItens().forEach(item -> item.setPedido(this));
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
