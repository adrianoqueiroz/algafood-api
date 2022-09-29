package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	private static final ModelMapper modelMapper = new ModelMapper();

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoModel);

		TemplateVariables pageVariables = new TemplateVariables(
			new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
			new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
		);

		TemplateVariables filterVariables = new TemplateVariables(
			new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
			new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM)
		);

		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		Link linkPedidos = Link.of(UriTemplate.of(pedidosUrl, pageVariables.concat(filterVariables)),"pedidos");
		pedidoModel.add(linkPedidos);

		pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
				.buscar(pedidoModel.getRestaurante().getId())).withSelfRel());

		pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
				.buscar(pedidoModel.getCliente().getId())).withSelfRel());

//		pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
//			.buscar(pedidoModel.getFormaPagamento().getId())).withSelfRel());

		return pedidoModel;
	}

	@Override
	public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(PedidoController.class).withSelfRel());
	}
}
