package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @Autowired
  private RestauranteRepository restauranteRepository;

  @GetMapping("/cozinhas/por-nome")
  public List<Cozinha> listar(String nome) {
    return cozinhaRepository.findAllByNome(nome);
  }

  @GetMapping("/cozinhas/unica-por-nome")
  public Optional<Cozinha> cozinhaPorNome(String nome) {
    return cozinhaRepository.findByNomeContainingIgnoreCase(nome);
  }

  @GetMapping("/restaurantes/por-nome")
  public List<Restaurante> restaurantePorNome(String nome, Long cozinhaId) {
    return restauranteRepository.consultarPorNome(nome,cozinhaId);
  }

  @GetMapping("/restaurantes/por-taxa-frete")
  public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
    return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
  }

  @GetMapping("/restaurantes/find-por-taxa-frete")
  public List<Restaurante> restaurantesFindPorTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
    return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
  }

  @GetMapping("/restaurantes/primeiro-por-nome")
  public Optional<Restaurante> primeiroPorNome(String nome) {
    return restauranteRepository.findFirstByNomeContaining(nome);
  }

  @GetMapping("/restaurantes/top2-por-nome")
  public List<Restaurante> top2PorNome(String nome) {
    return restauranteRepository.findTop2ByNomeContaining(nome);
  }

  @GetMapping("/restaurantes/count-por-cozinha-id")
  public int countPorCozinhaId(Long cozinhaId) {
    return restauranteRepository.countByCozinhaId(cozinhaId);
  }
}
