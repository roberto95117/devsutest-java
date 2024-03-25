package com.devsutest.demo.service;

import com.devsutest.demo.entity.Cuenta;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ICuentaService extends ICRUD<Cuenta, UUID> {
    BigDecimal actualizaSaldo(Integer tipoMov, BigDecimal valor, UUID idCuenta);
    List<Cuenta> obtenerPorIdCliente(Integer idCliente);
}
