package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.domain.model.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoModel {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeResumoModel cidade;

    private static final ModelMapper modelMapper = new ModelMapper();
    public EnderecoModel(Endereco endereco) {
        modelMapper.map(endereco, this);
    }


}
