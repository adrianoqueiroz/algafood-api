package com.algaworks.algafood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurante_id_generator")
  @SequenceGenerator(name = "restaurante_id_generator", sequenceName = "restaurante_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxaFrete;

  @ManyToOne
  @JoinColumn(name = "cozinha_id", nullable = false)
  private Cozinha cozinha;
}
