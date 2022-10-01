package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinksGenerator;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private LinksGenerator linksGenerator;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}


	@Override
	public UsuarioModel toModel(Usuario usuaro) {
		UsuarioModel usuarioModel = createModelWithId(usuaro.getId(), usuaro);

		modelMapper.map(usuaro, usuarioModel);

		Link collectionLink = linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios");
		usuarioModel.add(collectionLink);

		return usuarioModel;
	}

	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
			.add(linksGenerator.linkToUsuarios());
	}
}
