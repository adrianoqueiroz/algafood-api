package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Restaurante;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RestauranteModel {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;
    private Boolean active;
    private EnderecoModel endereco;
    private Boolean aberto;

    private static final ModelMapper modelMapper = new ModelMapper();

    public RestauranteModel(Restaurante restaurante) {
        modelMapper.map(restaurante, this);
    }
}
