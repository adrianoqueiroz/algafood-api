package com.algaworks.algafood.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
@NoArgsConstructor
public class CozinhaModel extends RepresentationModel<CozinhaModel> {
//    @JsonView(RestauranteView.Resumo.class)
    private Long id;
//    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
