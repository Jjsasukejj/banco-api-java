package com.banco.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.domain.enums.TipoCuenta;
import com.banco.api.dto.response.ReporteMovimientoResponse;
import com.banco.api.service.MovimientoService;

@WebMvcTest(MovimientoController.class)
class MovimientoControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoService movimientoService;
    
    @Test
    void reporteMovimientos_porClienteYFechas_retornaLista() throws Exception {
    	
    	ReporteMovimientoResponse dto = new ReporteMovimientoResponse();
    	
    	dto.setCliente("Jose Lema");
    	dto.setEstado(EstadoCuenta.ACTIVA);
    	dto.setFecha(LocalDate.now());
    	dto.setMovimiento(BigDecimal.valueOf(100));
    	dto.setNumeroCuenta("478758");
    	dto.setSaldoDisponible(BigDecimal.valueOf(1100));
    	dto.setSaldoInicial(BigDecimal.valueOf(1000));
    	dto.setTipoCuenta(TipoCuenta.AHORROS);

        when(movimientoService.reporteMovimientosPorCliente(
                eq(1L), any(), any()))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/movimientos/reporte")
                .param("clienteId", "1")
                .param("fechaInicio", "2026-01-01")
                .param("fechaFin", "2026-01-31"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].cliente").value("Jose Lema"));
    }
}
