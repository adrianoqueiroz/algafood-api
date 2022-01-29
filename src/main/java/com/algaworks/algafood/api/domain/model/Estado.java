package com.algaworks.algafood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Estado {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_id_generator")
  @SequenceGenerator(name = "estado_id_generator", sequenceName = "estado_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String sigla;
}
