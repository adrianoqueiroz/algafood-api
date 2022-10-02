package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinksGenerator;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private LinksGenerator linksGenerator;

	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}

	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel =
			createModelWithId(formaPagamento.getId(), formaPagamento);

		modelMapper.map(formaPagamento, formaPagamentoModel);

		formaPagamentoModel.add(linksGenerator.linkToFormasPagamento("formasPagamento"));

		return formaPagamentoModel;
	}

	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		return super.toCollectionModel(entities)
			.add(linksGenerator.linkToFormasPagamento());
	}
}
