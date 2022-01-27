package com.algaworks.algafoodapi.jpa;

import com.algaworks.algafoodapi.AlgafoodApiApplication;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
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
