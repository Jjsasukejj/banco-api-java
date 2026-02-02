package com.banco.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.banco.api.domain.entity.Cuenta;
import com.banco.api.dto.mapper.CuentaMapper;
import com.banco.api.dto.request.ActualizarCuentaRequest;
import com.banco.api.dto.request.CrearCuentaRequest;
import com.banco.api.dto.response.CuentaResponse;
import com.banco.api.service.CuentaService;

import jakarta.validation.Valid;
/**
 * Controller de cuentas
 * Usa DTOs para no exponer entidades.
 */
@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
	
	private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }
    
    @PostMapping("/cliente/{clienteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponse crearCuenta(@PathVariable Long clienteId, @Valid @RequestBody CrearCuentaRequest request) {
    	
    	Cuenta cuenta = new Cuenta();
    	cuenta.setNumeroCuenta(request.getNumeroCuenta());
    	cuenta.setTipoCuenta(request.getTipoCuenta());
    	cuenta.setSaldo(request.getSaldoInicial());
    	cuenta.setEstado(request.getEstado());
    	
    	return CuentaMapper.toResponse(cuentaService.crearCuenta(clienteId, cuenta));
    }
    
    @GetMapping("/{numeroCuenta}")
    public CuentaResponse obtenerCuenta(@PathVariable String numeroCuenta) {
        return CuentaMapper.toResponse(
                cuentaService.obtenerCuentaPorNumero(numeroCuenta)
        );
    }
    
    @GetMapping("/cliente/{clienteId}")
    public List<CuentaResponse> listarCuentasPorCliente(@PathVariable Long clienteId) {
        return cuentaService.listarCuentasPorCliente(clienteId)
                .stream()
                .map(CuentaMapper::toResponse)
                .toList();
    }
    
    @PutMapping("/{numeroCuenta}")
    public CuentaResponse actualizarCuenta(
            @PathVariable String numeroCuenta,
            @Valid @RequestBody ActualizarCuentaRequest request
    ) {
        return CuentaMapper.toResponse(cuentaService.actualizarCuenta(numeroCuenta, request));
    }
    
    @DeleteMapping("/{numeroCuenta}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCuenta(@PathVariable String numeroCuenta) {
        cuentaService.eliminarCuenta(numeroCuenta);
    }
    
    @GetMapping
    public List<CuentaResponse> listarCuentas() {
        return cuentaService.listarCuentas()
        		.stream()
        		.map(CuentaMapper::toResponse)
        		.toList();
    }
}
