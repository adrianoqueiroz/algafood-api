package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelAssembler {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	public ProdutoModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoModel.class);
	}
	
	public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toList());
	}
	
}
