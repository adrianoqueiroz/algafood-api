package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EstatisticasControllerOpenApi {
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
                                             @RequestParam(required = false, defaultValue = "+00:00") String timeOffset);

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(
        VendaDiariaFilter filter,
        @RequestParam(required = false, defaultValue = "+00:00") String timeOffset);
}
