package com.devsutest.demo.service.imp;

import com.devsutest.demo.entity.Movimiento;
import com.devsutest.demo.exception.BadRequestExp;
import com.devsutest.demo.exception.NoEncontradoExc;
import com.devsutest.demo.repository.IMovimientoRepo;
import com.devsutest.demo.service.ICuentaService;
import com.devsutest.demo.service.IMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

@Service
public class MovimientoImp implements IMovimientoService {

    IMovimientoRepo movimientoRepo;

    ICuentaService cuentaService;

    @Autowired
    public MovimientoImp(
        IMovimientoRepo movimientoRepo,
        ICuentaService cuentaService
    ){
        this.movimientoRepo = movimientoRepo;
        this.cuentaService = cuentaService;
    }
    @Override
    public Movimiento insertar(Movimiento objeto) {
        objeto.setFecha(LocalDate.now());;
        objeto.setSaldo(cuentaService.actualizaSaldo(objeto.getIdTipoMovimiento().getId(), objeto.getValor(), objeto.getNumeroCuenta().getId()));
        Movimiento movimiento1 = movimientoRepo.save(objeto);
        if(movimiento1.getId() == null) throw new BadRequestExp("No se pudo generar movimiento");
        return movimiento1;
    }

    @Override
    public Movimiento modificar(Movimiento objeto) {
        if(movimientoRepo.findById(objeto.getId()).isEmpty()) throw new NoEncontradoExc("No existe registro con el id ingresasdo");
        return movimientoRepo.save(objeto);
    }

    @Override
    public boolean eliminar(Integer id) {
        Movimiento mov = movimientoRepo.findById(id).orElseThrow(() -> new NoEncontradoExc("No existe registro con el id ingresasdo"));
        cuentaService.actualizaSaldo(mov.getIdTipoMovimiento().getId()  == 1 ? 2 : 1, mov.getValor(), mov.getNumeroCuenta().getId());
        movimientoRepo.deleteById(id);
        return movimientoRepo.findById(id).isPresent();
    }

    @Override
    public Movimiento obtener(Integer id) {
        return movimientoRepo.findById(id).orElseThrow(() -> new NoEncontradoExc("No existe registro con el id ingresasdo"));
    }

    @Override
    public Integer actualizaFecha(Integer id, String fecha) {
        if(movimientoRepo.findById(id).isEmpty()) throw new NoEncontradoExc("No existe registro con el id ingresasdo");
        return movimientoRepo.actualizaFecha(fecha, id);
    }
}
