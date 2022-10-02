package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinksGenerator;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelAssembler
    extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private LinksGenerator linksGenerator;

    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }

    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = createModelWithId(
            restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(linksGenerator.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(
            linksGenerator.linkToCozinha(restaurante.getCozinha().getId()));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
            .add(linksGenerator.linkToRestaurantes());
    }
}