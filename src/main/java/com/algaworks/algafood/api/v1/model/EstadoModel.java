package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class EstadoModel extends RepresentationModel<EstadoModel> {

    @Schema(description = "ID do estado", example = "1")
    private Long id;

    @Schema(description = "Nome do estado", example = "Bahia")
    private String nome;
}