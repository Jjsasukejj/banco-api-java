package com.banco.api.controller;


import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.banco.api.dto.mapper.MovimientoMapper;
import com.banco.api.dto.request.MovimientoRequest;
import com.banco.api.dto.response.MovimientoResponse;
import com.banco.api.dto.response.ReporteMovimientoPdfResponse;
import com.banco.api.dto.response.ReporteMovimientoResponse;
import com.banco.api.service.MovimientoService;
import com.banco.api.service.impl.ReporteMovimientoPdfGenerator;

import jakarta.validation.Valid;
/**
 * Controller de movimientos
 * Usa DTOs para no exponer entidades.
 */
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {
	
	private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }
    
    @PostMapping("/depositos/{numeroCuenta}")
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse depositar(@PathVariable String numeroCuenta, @Valid @RequestBody MovimientoRequest request) {
        return MovimientoMapper.toResponse(movimientoService.realizarDeposito(numeroCuenta, request.getMonto()));
    }
    
    @PostMapping("/retiros/{numeroCuenta}")
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse retirar(@PathVariable String numeroCuenta, @Valid @RequestBody MovimientoRequest request) {
        return MovimientoMapper.toResponse(movimientoService.realizarRetiro(numeroCuenta, request.getMonto()));
    }
    
    /**
     * Reporte de movimientos por cliente y rango de fechas.
     */
    @GetMapping("/reporte")
    public List<ReporteMovimientoResponse> reporteMovimientos(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return movimientoService.reporteMovimientosPorCliente(clienteId, fechaInicio, fechaFin);
    }
    
    @GetMapping("/reporte/pdf")
    public ReporteMovimientoPdfResponse obtenerReportePdf(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        // Reutilizamos el reporte existente
        List<ReporteMovimientoResponse> reporte =
                movimientoService.reporteMovimientosPorCliente(clienteId, fechaInicio, fechaFin);

        // Generamos PDF
        byte[] pdfBytes = ReporteMovimientoPdfGenerator.generarPdf(reporte);

        // Convertimos a Base64
        String base64 = Base64.getEncoder().encodeToString(pdfBytes);

        // Retornamos DTO
        return new ReporteMovimientoPdfResponse(
                base64,
                "estado_cuenta_cliente_" + clienteId + ".pdf"
        );
    }
}
