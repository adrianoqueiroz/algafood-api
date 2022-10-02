package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinksGenerator;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private LinksGenerator linksGenerator;
	private static final ModelMapper modelMapper = new ModelMapper();

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoModel);


		pedidoModel.add(linksGenerator.linkToPedidos("pedidos"));

		if(pedido.podeSerConfirmado()) {
			pedidoModel.add(linksGenerator.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		}

		if(pedido.podeSerEntregue()) {
			pedidoModel.add(linksGenerator.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		}

		if(pedido.podeSerCancelado()) {
			pedidoModel.add(linksGenerator.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		}

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
