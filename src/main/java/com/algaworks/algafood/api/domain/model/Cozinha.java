package com.algaworks.algafood.api.domain.model;

import com.algaworks.algafood.api.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cozinha {
  @NotNull(groups = Groups.CozinhaId.class)
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cozinha_id_generator")
  @SequenceGenerator(name = "cozinha_id_generator", sequenceName = "cozinha_id_seq", allocationSize = 1)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String nome;

  @OneToMany(mappedBy = "cozinha")
  List<Restaurante> restaurantes;
}
