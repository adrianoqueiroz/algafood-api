package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ItemPedido {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_pedido_id_generator")
  @SequenceGenerator(name = "item_pedido_id_generator", sequenceName = "item_pedido_id_seq", allocationSize = 1)
  private Long id;

  private BigDecimal precoUnitario;
  private BigDecimal precoTotal;
  private Integer quantidade;
  private String observacao;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Produto produto;

  public void calcularPrecoTotal() {
    BigDecimal precoUnitario = this.getPrecoUnitario();
    Integer quantidade = this.getQuantidade();

    if (precoUnitario == null) {
      precoUnitario = BigDecimal.ZERO;
    }

    if (quantidade == null) {
      quantidade = 0;
    }

    this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ItemPedido that = (ItemPedido) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
