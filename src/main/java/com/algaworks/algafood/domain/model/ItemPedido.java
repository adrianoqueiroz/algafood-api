package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_pedido_id_generator")
  @SequenceGenerator(name = "item_pedido_id_generator", sequenceName = "item_pedido_id_seq", allocationSize = 1)
  private Long id;

  private Integer quantidade;
  private BigDecimal precoUnitario;
  private BigDecimal precoTotal;
  private String observacao;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Produto produto;
}
