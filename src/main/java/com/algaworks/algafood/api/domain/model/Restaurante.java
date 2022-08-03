package com.algaworks.algafood.api.domain.model;

import com.algaworks.algafood.api.core.validation.Groups;
import com.algaworks.algafood.api.core.validation.Multiplo;
import com.algaworks.algafood.api.core.validation.TaxaFrete;
import com.algaworks.algafood.api.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@ValorZeroIncluiDescricao(valorField = "taxaFrete",
    descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
public class Restaurante {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurante_id_generator")
  @SequenceGenerator(name = "restaurante_id_generator", sequenceName = "restaurante_id_seq", allocationSize = 1)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String nome;

  @NotNull
  @TaxaFrete
  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxaFrete;

  @NotNull
  @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
  @Valid
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
  @ToString.Exclude
  private List<FormaPagamento> formasPagamento;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurante")
  @ToString.Exclude
  private List<Produto> produtos;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Restaurante that = (Restaurante) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
