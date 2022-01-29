package com.algaworks.algafood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FormaPagamento {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forma_pagamento_id_generator")
  @SequenceGenerator(name = "forma_pagamento_id_generator", sequenceName = "forma_pagamento_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String descricao;
}
