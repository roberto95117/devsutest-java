package com.devsutest.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.devsutest.demo.entity.Persona}
 */
@Value
@Builder
public class ClientePersonaDto implements Serializable {
    Long id;
    String nombre;
    String genero;
    Integer edad;
    String direccion;
    Long telefono;
    String contrasenia;
    Integer estado;
}