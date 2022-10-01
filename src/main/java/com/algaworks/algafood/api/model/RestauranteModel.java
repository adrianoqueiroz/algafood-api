package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
public class RestauranteModel extends RepresentationModel<RestauranteModel> {
//    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;
//    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;
//    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;
//    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;

    private Boolean active;
    private EnderecoModel endereco;
    private Boolean aberto;

    private static final ModelMapper modelMapper = new ModelMapper();

    public RestauranteModel(Restaurante restaurante) {
        modelMapper.map(restaurante, this);
    }
}
