package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.domain.model.Restaurante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteInput {

    private static final ModelMapper modelMapper = new ModelMapper();
    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInput cozinha;

    public RestauranteInput(Restaurante restauranteAtual) {
        this.nome = restauranteAtual.getNome();
        this.taxaFrete = restauranteAtual.getTaxaFrete();
        this.cozinha = new CozinhaIdInput(restauranteAtual.getCozinha());
    }

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }
}
