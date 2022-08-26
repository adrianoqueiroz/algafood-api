package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.domain.model.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class EnderecoInput {
    @NotBlank
    private String cep;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String numero;
    private String complemento;

    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;

    private static final ModelMapper modelMapper = new ModelMapper();
    public EnderecoInput(Endereco endereco) {
        modelMapper.map(endereco, this);
    }
}
