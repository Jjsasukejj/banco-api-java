package com.banco.api.controller;

import com.banco.api.domain.entity.Cuenta;
import com.banco.api.dto.mapper.CuentaMapper;
import com.banco.api.dto.request.CrearCuentaRequest;
import com.banco.api.dto.response.CuentaResponse;
import com.banco.api.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    	
    	return CuentaMapper.toResponse(cuentaService.crearCuenta(clienteId, cuenta));
    }
    
    @GetMapping("/{numeroCuenta}")
    public CuentaResponse obtenerCuenta(@PathVariable String numeroCuenta) {
        return CuentaMapper.toResponse(
                cuentaService.obtenerCuentaPorNumero(numeroCuenta)
        );
    }
}
