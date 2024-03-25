package com.devsutest.demo.controller;

import com.devsutest.demo.dto.DetalleMovimientoDto;
import com.devsutest.demo.exception.NoEncontradoExc;
import com.devsutest.demo.model.RespuestaHttp;
import com.devsutest.demo.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    private IReporteService reporteService;

    @Autowired
    public ReportesController(
        IReporteService reporteService
    ){
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<RespuestaHttp> detalleMovimientos(@RequestParam("fechas") String fechas, @RequestParam("cliente") Integer cliente){
        RespuestaHttp res = new RespuestaHttp();
        List<DetalleMovimientoDto> historial= reporteService.detalleMovimientoCuentaUsuario(fechas, cliente);
        if(historial.isEmpty()) throw new NoEncontradoExc("No se encontraron datos con los parametros ingresados");
        res.setCode(200);
        res.setDatos(historial);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
