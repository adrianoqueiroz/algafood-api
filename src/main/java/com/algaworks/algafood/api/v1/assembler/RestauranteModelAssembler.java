package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinksGenerator;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler
	extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private LinksGenerator linksGenerator;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);

		if (restaurante.ativacaoPermitida()) {
			restauranteModel.add(
				linksGenerator.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
		}

		if (restaurante.inativacaoPermitida()) {
			restauranteModel.add(
				linksGenerator.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
		}

		if (restaurante.aberturaPermitida()) {
			restauranteModel.add(
				linksGenerator.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}

		if (restaurante.fechamentoPermitido()) {
			restauranteModel.add(
				linksGenerator.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}

		restauranteModel.add(linksGenerator.linkToRestaurantes("restaurantes"));

		restauranteModel.getCozinha().add(
			linksGenerator.linkToCozinha(restaurante.getCozinha().getId()));

		restauranteModel.getEndereco().getCidade().add(
			linksGenerator.linkToCidade(restaurante.getEndereco().getCidade().getId()));

		restauranteModel.add(linksGenerator.linkToRestauranteFormasPagamento(restaurante.getId(),
			"formas-pagamento"));

		restauranteModel.add(linksGenerator.linkToRestauranteResponsaveis(restaurante.getId(),
			"responsaveis"));

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
			.add(linksGenerator.linkToRestaurantes());
	}
}