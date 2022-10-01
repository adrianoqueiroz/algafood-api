package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

    private UUID codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime createdAt;
    private RestauranteApenasNomeModel restaurante;
//    private RestauranteResumoModel restaurante;
        private UsuarioModel cliente;
//    private String nomeCliente;
}
