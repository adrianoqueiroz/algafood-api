package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinksGenerator;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeModelAssembler
    extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private LinksGenerator linksGenerator;

    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteModel = createModelWithId(
            restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(linksGenerator.linkToRestaurantes("restaurantes"));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
            .add(linksGenerator.linkToRestaurantes());
    }
}