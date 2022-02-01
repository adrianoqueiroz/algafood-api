package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
