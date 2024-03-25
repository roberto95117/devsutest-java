package com.devsutest.demo.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleMovimientoDto {
    String fecha;
    String nombreCliente;
    String tipoCuenta;
    BigDecimal saldoInicial;
    String estadoCuenta;
    BigDecimal valorMovimiento;
    String tipoMovimiento;
    BigDecimal saldo;
}
