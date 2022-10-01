package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinksGenerator;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private LinksGenerator linksGenerator;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	@Override
	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoResumoModel);

		pedidoResumoModel.add(linksGenerator.linkToPedidos("pedidos"));

		return pedidoResumoModel;
	}

	@Override
	public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(PedidoController .class).withSelfRel());
	}
}
