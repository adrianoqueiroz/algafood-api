package com.algaworks.algafood.api.domain.repository;

import com.algaworks.algafood.api.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
