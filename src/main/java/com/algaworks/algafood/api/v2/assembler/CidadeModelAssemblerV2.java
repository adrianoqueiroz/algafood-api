package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v2.LinksGeneratorV2;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

	private static final ModelMapper modelMapper = new ModelMapper();
	private LinksGeneratorV2 linksGenerator = new LinksGeneratorV2();;
	
	public CidadeModelAssemblerV2() {
		super(CidadeControllerV2.class, CidadeModelV2.class);
	}
	
	@Override
	public CidadeModelV2 toModel(Cidade cidade) {
		CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeModel);

		Link collectionLink = linkTo(methodOn(CidadeControllerV2.class).listar()).withRel("cidades");
		cidadeModel.add(collectionLink);

		return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
			.add(linksGenerator.linkToCidades());
	}
}
