package com.devsutest.demo.controller;


import com.devsutest.demo.dto.MovimientoDto;
import com.devsutest.demo.entity.Cuenta;
import com.devsutest.demo.entity.Movimiento;
import com.devsutest.demo.entity.TipoMovimiento;
import com.devsutest.demo.exception.BadRequestExp;
import com.devsutest.demo.model.RespuestaHttp;
import com.devsutest.demo.service.ICuentaService;
import com.devsutest.demo.service.IMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private IMovimientoService movimientoService;

    @Autowired
    public MovimientoController(
            IMovimientoService movimientoService
    ){
        this.movimientoService = movimientoService;
    }

    @PostMapping()
    public ResponseEntity<RespuestaHttp> agregar(@RequestBody MovimientoDto dto){
        RespuestaHttp res = new RespuestaHttp();
        Movimiento movimiento = new Movimiento();

        TipoMovimiento tipoMovimiento = new TipoMovimiento();
        tipoMovimiento.setId(dto.getIdTipoMovimientoId());

        Cuenta cuenta = new Cuenta();
        cuenta.setId(dto.getNumeroCuentaId());

        movimiento.setIdTipoMovimiento(tipoMovimiento);
        movimiento.setValor(dto.getValor());
        movimiento.setNumeroCuenta(cuenta);

        res.setCode(201);
        res.setMsj("Movimiento realizado");
        res.setDatos(movimientoService.insertar(movimiento));
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<RespuestaHttp> obtener(@RequestParam("id") Integer id){
        RespuestaHttp res = new RespuestaHttp();

        res.setCode(200);
        res.setDatos(movimientoService.obtener(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<RespuestaHttp> eliminar(@RequestParam("id") Integer id){
        RespuestaHttp res = new RespuestaHttp();
        if(movimientoService.eliminar(id)) throw new BadRequestExp("No fue posible eliminar el registro");
        res.setCode(200);
        res.setMsj("Registro eliminado");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<RespuestaHttp> actualizaFecha(@RequestParam("id") Integer id, @RequestParam("fecha") String fecha){
        RespuestaHttp res = new RespuestaHttp();
        Integer upd = movimientoService.actualizaFecha(id, fecha);
        if(upd > 0){
            res.setCode(200);
            res.setMsj("Fecha actualizada");
        }else {
            throw new BadRequestExp("No se puedo actualizar el registro indicado");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
