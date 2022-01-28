package com.algaworks.algafood.api.jpa;

import com.algaworks.algafood.api.domain.repository.PermissaoRepository;
import com.algaworks.algafood.api.AlgafoodApiApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ConsultaPermissaoMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    PermissaoRepository permissaoRepository = context.getBean(PermissaoRepository.class);
    permissaoRepository.listar().forEach(permissao -> System.out.printf("%s - %s\n", permissao.getNome(), permissao.getDescricao()));
  }
}
