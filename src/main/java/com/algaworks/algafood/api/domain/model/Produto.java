package com.algaworks.algafood.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_id_generator")
  @SequenceGenerator(name = "produto_id_generator", sequenceName = "produto_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column
  private String descricao;

  @Column(nullable = false)
  private BigDecimal preco;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "restaurante_id")
  private Restaurante restaurante;

  @Column(nullable = false)
  private Boolean ativo;
}
