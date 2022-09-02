package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@ValorZeroIncluiDescricao(valorField = "taxaFrete",
    descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
public class Restaurante {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurante_id_generator")
  @SequenceGenerator(name = "restaurante_id_generator", sequenceName = "restaurante_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @TaxaFrete
  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxaFrete;

  @ManyToOne //(fetch = FetchType.LAZY)
  @JoinColumn(name = "cozinha_id", nullable = false)
  private Cozinha cozinha;

  @Embedded
  private Endereco endereco;

  private Boolean active = Boolean.TRUE;

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
  private Set<FormaPagamento> formasPagamento = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "restaurante_usuario_responsavel",
      joinColumns = @JoinColumn(name = "restaurante_id"),
      inverseJoinColumns = @JoinColumn(name = "usuario_id"))
  private Set<Usuario> responsaveis = new HashSet<>();

  @OneToMany(mappedBy = "restaurante")
  @ToString.Exclude
  private List<Produto> produtos = new ArrayList<>();

  private Boolean aberto = Boolean.FALSE;

  public void abrir() {
    setAberto(true);
  }

  public void fechar() {
    setAberto(false);
  }

  public boolean removerResponsavel(Usuario usuario) {
    return getResponsaveis().remove(usuario);
  }

  public boolean adicionarResponsavel(Usuario usuario) {
    return getResponsaveis().add(usuario);
  }

  public void ativar() {
        setActive(true);
    }

    public void inativar() {
        setActive(false);
    }

  public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
    return getFormasPagamento().contains(formaPagamento);
  }

  public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
    return !aceitaFormaPagamento(formaPagamento);
  }

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

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
      return getFormasPagamento().remove(formaPagamento);
    }

  public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
    return getFormasPagamento().add(formaPagamento);
  }
}
