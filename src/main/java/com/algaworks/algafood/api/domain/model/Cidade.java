package com.algaworks.algafood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_id_generator")
  @SequenceGenerator(name = "cidade_id_generator", sequenceName = "cidade_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @ManyToOne
  @JoinColumn(name = "estado_id", nullable = false)
  private Estado estado;
}
