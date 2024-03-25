package com.devsutest.demo.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link com.devsutest.demo.entity.Movimiento}
 */
@Value
public class MovimientoDto implements Serializable {
    Integer id;
    LocalDate fecha;
    Integer idTipoMovimientoId;
    BigDecimal valor;
    BigDecimal saldo;
    UUID numeroCuentaId;
}