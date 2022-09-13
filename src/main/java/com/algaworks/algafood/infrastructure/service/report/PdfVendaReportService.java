package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PdfVendaReportService implements VendaReportService {

    private final VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas_diarias_A4.jasper");

            var parametres = new HashMap<String, Object>();
            parametres.put("REPORT_LOCALE", new Locale("pt", "BR"));

//            var vendasDiarias = vendaQueryService.consultarVendasDiarias(filter, timeOffset);

            //example value
            VendaDiaria vendaDiaria = new VendaDiaria();
            vendaDiaria.setTotalVendas(10L);
            vendaDiaria.setTotalFaturado(BigDecimal.valueOf(1000.0));

            var vendasDiariasExample = new ArrayList<>();
            vendasDiariasExample.add(vendaDiaria);
            vendasDiariasExample.add(vendaDiaria);
            vendasDiariasExample.add(vendaDiaria);
            //end example value

            var dataSource = new JRBeanCollectionDataSource(vendasDiariasExample);

            var jasperPrint = JasperFillManager.fillReport(inputStream, parametres, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}
