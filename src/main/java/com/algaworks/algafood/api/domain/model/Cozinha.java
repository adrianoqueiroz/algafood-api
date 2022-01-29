package com.algaworks.algafood.api.domain.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@JsonRootName("cozinha")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cozinha_id_generator")
  @SequenceGenerator(name = "cozinha_id_generator", sequenceName = "cozinha_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String nome;
}
