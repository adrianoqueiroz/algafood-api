package com.algaworks.algafood.api.jpa;

import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import com.algaworks.algafood.api.AlgafoodApiApplication;
import com.algaworks.algafood.api.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AlteracaoCozinhaMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);

    Cozinha cozinha = new Cozinha();
    cozinha.setId(1L);
    cozinha.setNome("Japonesa");

    cozinha = cadastroCozinha.salvar(cozinha);

    System.out.printf("%d - %s%n", cozinha.getId(), cozinha.getNome());
  }
}
