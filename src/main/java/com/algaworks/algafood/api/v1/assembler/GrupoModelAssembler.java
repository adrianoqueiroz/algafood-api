package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoModelAssembler {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	public GrupoModel toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoModel.class);
	}
	
	public List<GrupoModel> toCollectionModel(Collection<Grupo> grupos) {
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
	}
	
}
