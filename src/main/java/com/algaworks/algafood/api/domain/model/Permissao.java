package com.algaworks.algafood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Permissao {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissao_id_generator")
  @SequenceGenerator(name = "permissao_id_generator", sequenceName = "permissao_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column
  private String descricao;
}
