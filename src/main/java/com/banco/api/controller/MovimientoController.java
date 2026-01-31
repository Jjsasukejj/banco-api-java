package com.banco.api.controller;


import com.banco.api.dto.mapper.MovimientoMapper;
import com.banco.api.dto.request.MovimientoRequest;
import com.banco.api.dto.response.MovimientoResponse;
import com.banco.api.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
}
