package com.devsutest.demo.service.imp;

import com.devsutest.demo.dto.DetalleMovimientoDto;
import com.devsutest.demo.enums.SqlReporteEnum;
import com.devsutest.demo.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServiceImp implements IReporteService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReporteServiceImp(
        JdbcTemplate jdbcTemplate
    ){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DetalleMovimientoDto> detalleMovimientoCuentaUsuario(String fechas, Integer idCliente) {
        RowMapper<DetalleMovimientoDto> mapper = new BeanPropertyRowMapper<>(DetalleMovimientoDto.class);
        String[] fechasArr = fechas.split("al");
        return jdbcTemplate.query(SqlReporteEnum.DETALLE_MOVIMIENTO_CLIENTE_FECHA.getValue(), mapper, idCliente, fechasArr[0], fechasArr[1]);
    }
}
