package com.algaworks.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@JsonFilter("pedidoFilter")
public class PedidoResumoModel {

    private UUID codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime createdAt;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;
}
