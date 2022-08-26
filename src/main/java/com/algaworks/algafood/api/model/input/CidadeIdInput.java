package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.domain.model.Cozinha;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CidadeIdInput {
    @NotNull
    private Long id;

    public CidadeIdInput(Cozinha cozinha) {
        this.id = cozinha.getId();
    }
}
