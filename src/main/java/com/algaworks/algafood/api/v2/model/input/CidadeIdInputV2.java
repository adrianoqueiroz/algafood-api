package com.algaworks.algafood.api.v2.model.input;

import com.algaworks.algafood.domain.model.Cozinha;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CidadeIdInputV2 {
    @NotBlank
    private String nomeCidade;

    @NotNull
    private Long idEstado;
}
