package com.devsutest.demo.service;

import com.devsutest.demo.dto.DetalleMovimientoDto;

import java.util.List;

public interface IReporteService {

    List<DetalleMovimientoDto> detalleMovimientoCuentaUsuario(String fechas, Integer idCliente);
}
