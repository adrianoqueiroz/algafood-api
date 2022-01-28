package com.algaworks.algafood.api.jpa;

import com.algaworks.algafood.api.AlgafoodApiApplication;
import com.algaworks.algafood.api.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ConsultaEstadoMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    EstadoRepository estadoRepository = context.getBean(EstadoRepository.class);
    estadoRepository.listar().forEach(estado -> System.out.println(estado.getNome()));
  }
}
