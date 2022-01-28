package com.algaworks.algafood.api.jpa;

import com.algaworks.algafood.api.AlgafoodApiApplication;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ExclusaoCozinhaMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);

    cadastroCozinha.remover(1L);
  }
}
