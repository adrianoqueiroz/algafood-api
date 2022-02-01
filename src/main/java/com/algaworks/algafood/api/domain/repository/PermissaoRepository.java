package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
