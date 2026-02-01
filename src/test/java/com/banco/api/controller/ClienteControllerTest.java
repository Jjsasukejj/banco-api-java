package com.banco.api.controller;

import com.banco.api.domain.entity.Cliente;
import com.banco.api.domain.enums.Genero;
import com.banco.api.dto.request.CrearClienteRequest;
import com.banco.api.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void crearCliente_debeRetornar201YClienteCreado() throws Exception {

    	// Arrange (datos de entrada)
        CrearClienteRequest request = new CrearClienteRequest();
        request.setNombre("Jose Lema");
        request.setGenero(Genero.MASCULINO);
        request.setEdad(35);
        request.setIdentificacion("0102030405");
        request.setDireccion("Otavalo sn y principal");
        request.setTelefono("098254785");
        request.setContrasena("1234");

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNombre("Jose Lema");
        clienteMock.setGenero(Genero.MASCULINO);
        clienteMock.setEdad(35);
        clienteMock.setIdentificacion("0102030405");
        clienteMock.setDireccion("Otavalo sn y principal");
        clienteMock.setTelefono("098254785");

        Mockito.when(clienteService.crearCliente(Mockito.any(Cliente.class)))
               .thenReturn(clienteMock);

        // Act + Assert
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Jose Lema"))
                .andExpect(jsonPath("$.identificacion").value("0102030405"));
    }
    
    @Test
    void listarClientes_debeRetornarLista() throws Exception {
    	
    	// Arrange (datos de entrada)
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Jose Lema");

        Mockito.when(clienteService.listarClientes())
               .thenReturn(List.of(cliente));

        // Act + Assert
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/clientes")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Jose Lema"));
    }
}
