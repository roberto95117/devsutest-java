package com.devsutest.demo.dto;

import com.devsutest.demo.entity.TipoCuenta;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link com.devsutest.demo.entity.Cuenta}
 */
@Value
public class CuentaDto implements Serializable {
    UUID id;
    TipoCuenta idTipoCuenta;
    BigDecimal saldoInicial;
    Integer estado;
    Integer idCliente;
}