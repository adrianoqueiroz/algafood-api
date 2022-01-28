package com.algaworks.algafood.api.jpa;

import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import com.algaworks.algafood.api.AlgafoodApiApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ConsultaRestauranteMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    RestauranteRepository restauranteRepository = context.getBean(RestauranteRepository.class);
    restauranteRepository.listar().forEach(restaurante ->
        System.out.printf("%s - %f - %s\n", restaurante.getNome(), restaurante.getTaxaFrete(), restaurante.getCozinha().getNome())
    );
  }
}
