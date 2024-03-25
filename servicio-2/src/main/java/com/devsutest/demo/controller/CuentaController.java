package com.devsutest.demo.controller;

import com.devsutest.demo.dto.CuentaDto;
import com.devsutest.demo.entity.Cuenta;
import com.devsutest.demo.exception.BadRequestExp;
import com.devsutest.demo.model.RespuestaHttp;
import com.devsutest.demo.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {


    private ICuentaService cuentaService;

    @Autowired
    public CuentaController(
        ICuentaService cuentaService
    ){
        this.cuentaService = cuentaService;
    }

    @PostMapping()
    public ResponseEntity<RespuestaHttp> agregar(@RequestBody CuentaDto dto){
        Cuenta c = new Cuenta();
        c.setEstado(dto.getEstado());
        c.setIdCliente(dto.getIdCliente());
        c.setSaldoInicial(dto.getSaldoInicial());
        c.setIdTipoCuenta(dto.getIdTipoCuenta());
        c.setSaldoDisponible(dto.getSaldoInicial());

        c = cuentaService.insertar(c);

        if(c.getId() == null) throw new BadRequestExp("No fue posible crear cuenta");

        RespuestaHttp res = new RespuestaHttp();
        res.setCode(201);
        res.setMsj("Cuenta creada");
        res.setDatos(c);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaHttp> obtener(@PathVariable("id") UUID id){
        RespuestaHttp res = new RespuestaHttp();
        res.setCode(200);
        res.setDatos(cuentaService.obtener(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<RespuestaHttp> obtener(@PathVariable("idCliente") Integer idCliente){
        RespuestaHttp res = new RespuestaHttp();
        res.setCode(200);
        res.setDatos(cuentaService.obtenerPorIdCliente(idCliente));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaHttp> eliminar(@PathVariable("id") UUID id){
        RespuestaHttp res = new RespuestaHttp();
        if(cuentaService.eliminar(id)) throw new BadRequestExp("No se pudo eliminar cuenta");
        res.setCode(200);
        res.setMsj("Cuenta eliminada");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RespuestaHttp> modificar(@RequestBody CuentaDto dto){
        Cuenta c = new Cuenta();
        c.setId(dto.getId());
        c.setEstado(dto.getEstado());
        c.setIdCliente(dto.getIdCliente());
        c.setSaldoInicial(dto.getSaldoInicial());
        c.setIdTipoCuenta(dto.getIdTipoCuenta());

        c = cuentaService.modificar(c);

        RespuestaHttp res = new RespuestaHttp();
        res.setCode(200);
        res.setMsj("Cuenta modificada");
        res.setDatos(c);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
