package com.devsutest.demo.service;

import com.devsutest.demo.entity.Movimiento;

public interface IMovimientoService extends ICRUD<Movimiento, Integer> {

    Integer actualizaFecha(Integer id, String fecha);
}
