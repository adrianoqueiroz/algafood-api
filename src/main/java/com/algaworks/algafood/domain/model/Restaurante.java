package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
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
  @ManyToOne //(fetch = FetchType.LAZY)
  @JoinColumn(name = "cozinha_id", nullable = false)
  private Cozinha cozinha;

  @Embedded
  private Endereco endereco;

  @CreationTimestamp
  @Column(nullable = false, columnDefinition = "timestamp")
  private OffsetDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false, columnDefinition = "timestamp")
  private OffsetDateTime updatedAt;

  @ManyToMany
  @JoinTable(name = "restaurante_has_forma_pagamento",
      joinColumns = @JoinColumn(name = "restaurante_id"),
      inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
  @ToString.Exclude
  private List<FormaPagamento> formasPagamento;

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
