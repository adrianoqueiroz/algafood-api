package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoModelAssembler {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	public PermissaoModel toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoModel.class);
	}
	
	public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
		return permissoes.stream()
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}
	
}
