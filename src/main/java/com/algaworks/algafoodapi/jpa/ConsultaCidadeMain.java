package com.algaworks.algafoodapi.jpa;

import com.algaworks.algafoodapi.AlgafoodApiApplication;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ConsultaCidadeMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    CidadeRepository cidadeRepository = context.getBean(CidadeRepository.class);
    cidadeRepository.listar().forEach(cidade ->
        System.out.printf("%s - %s\n", cidade.getNome(), cidade.getEstado().getSigla()));
  }
}
