package com.devsutest.demo.controller;


import com.devsutest.demo.dto.ClientePersonaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.hamcrest.core.Is;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    private static final String CLIENTE_PATH = "/clientes";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerClientePorId() throws Exception{
        Integer id = 3;
        this.mockMvc.perform(get(CLIENTE_PATH + "/{idCliente}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.datos.id", Is.is(3)));
    }

    @Test
    void noEncontradoClientePorId() throws Exception{
        Integer id = 99;
        this.mockMvc.perform(get(CLIENTE_PATH + "/{idCliente}", id)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msj", Is.is("No existe informacion de cliente con id "+ id)));
    }

    @Test
    void eliminarClienteConCuentaExistente() throws Exception{
        Integer id = 3;
        this.mockMvc.perform(delete(CLIENTE_PATH + "/{idCliente}", id)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msj", Is.is("No se puede eliminar cliente, tiene cuenta asociada")));
    }

    @Test
    void crearCliente() throws Exception{
        long leftLimit = 1L;
        long rightLimit = 10000000000000L;
        long id = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        ClientePersonaDto resource = ClientePersonaDto.builder().nombre("Roberto Nuevo").genero("masculino")
                .edad(29).direccion("Guatemala ciudad").telefono(1234569L).contrasenia("213").estado(1)
                .id(id).build();

        this.mockMvc.perform(post(CLIENTE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.datos.identificacion.id", Is.is(resource.getId())));
    }
}
