package com.algaworks.algafood.api.jpa;

import com.algaworks.algafood.api.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.api.AlgafoodApiApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ConsultaFormaPagamentoMain {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);

    FormaPagamentoRepository formaPagamentoRepository = context.getBean(FormaPagamentoRepository.class);
    formaPagamentoRepository.listar().forEach(formaPagamento -> System.out.println(formaPagamento.getDescricao()));
  }
}
