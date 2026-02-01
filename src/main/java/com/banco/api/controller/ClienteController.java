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

import com.banco.api.domain.entity.Cliente;
import com.banco.api.dto.mapper.ClienteMapper;
import com.banco.api.dto.request.ActualizarClienteRequest;
import com.banco.api.dto.request.CrearClienteRequest;
import com.banco.api.dto.response.ClienteResponse;
import com.banco.api.service.ClienteService;

import jakarta.validation.Valid;
/**
 * Controller de clientes
 * Usa DTOs para no exponer entidades.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crearCliente(@Valid @RequestBody CrearClienteRequest request) {
    	Cliente cliente = new Cliente();
    	cliente.setNombre(request.getNombre());
        cliente.setGenero(request.getGenero());
        cliente.setEdad(request.getEdad());
        cliente.setIdentificacion(request.getIdentificacion());
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        cliente.setContrasena(request.getContrasena());
        
        return ClienteMapper.toResponse(clienteService.crearCliente(cliente));
    }
    
    @GetMapping
    public List<ClienteResponse> listarClientes() {
    	return clienteService.listarClientes()
    			.stream()
    			.map(ClienteMapper::toResponse)
    			.toList();
    }
    
    @GetMapping("/{id}")
    public ClienteResponse obtenerCliente(@PathVariable Long id) {
    	return ClienteMapper.toResponse(clienteService.obtenerClientePorId(id));
    }
    
    /**
     * Inactivar cliente (borrado logico)
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivarCliente(@PathVariable Long id) {
    	clienteService.inactivarCliente(id);
    }
    
    /**
     * Actualizar cliente
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public ClienteResponse actualizarCliente(@PathVariable Long id, @Valid @RequestBody ActualizarClienteRequest request) {
        return ClienteMapper.toResponse(
                clienteService.actualizarCliente(id, request)
        );
    }
}
