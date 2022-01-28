package com.algaworks.algafood.api.jpa;

import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import com.algaworks.algafood.api.AlgafoodApiApplication;
import com.algaworks.algafood.api.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class InclusaoCozinhaMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);

    Cozinha cozinha = new Cozinha();
    cozinha.setNome("Brasileira");

    Cozinha cozinha2 = new Cozinha();
    cozinha2.setNome("Japonesa");

    cozinha = cadastroCozinha.salvar(cozinha);
    cozinha2 =  cadastroCozinha.salvar(cozinha2);

    System.out.printf("%d - %s%n", cozinha.getId(), cozinha.getNome());
    System.out.printf("%d - %s%n", cozinha2.getId(), cozinha2.getNome());
  }
}
