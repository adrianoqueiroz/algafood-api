package com.algaworks.algafood.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

//  @JsonIgnore
  @JsonIgnoreProperties("hibernateLazyInitializer")
  @ManyToOne //(fetch = FetchType.LAZY)
  @JoinColumn(name = "cozinha_id", nullable = false)
  private Cozinha cozinha;

  @JsonIgnore
  @Embedded
  private Endereco endereco;

  @JsonIgnore
  @CreationTimestamp
  @Column(nullable = false, columnDefinition = "timestamp")
  private LocalDateTime createdAt;

  @JsonIgnore
  @UpdateTimestamp
  @Column(nullable = false, columnDefinition = "timestamp")
  private LocalDateTime updatedAt;

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "restaurante_has_forma_pagamento",
      joinColumns = @JoinColumn(name = "restaurante_id"),
      inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
  private List<FormaPagamento> formasPagamento;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurante")
  private List<Produto> produtos;
}
