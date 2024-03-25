package com.devsutest.demo.service.imp;

import com.devsutest.demo.entity.Cuenta;
import com.devsutest.demo.exception.BadRequestExp;
import com.devsutest.demo.exception.NoEncontradoExc;
import com.devsutest.demo.repository.ICuentaRepo;
import com.devsutest.demo.service.ICuentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CuentaServiceImp implements ICuentaService {

    private ICuentaRepo cuentaRepo;

    public CuentaServiceImp (
        ICuentaRepo cuentaRepo
    ){
        this.cuentaRepo = cuentaRepo;
    }
    @Override
    public Cuenta insertar(Cuenta objeto) {
        return cuentaRepo.save(objeto);
    }

    @Override
    public Cuenta modificar(Cuenta objeto) {
        if(cuentaRepo.findById(objeto.getId()).isEmpty()) throw new NoEncontradoExc("No existe registro para actualizar");
        return cuentaRepo.save(objeto);
    }

    @Override
    public boolean eliminar(UUID id) {
        if(cuentaRepo.findById(id).isEmpty()) throw new NoEncontradoExc("No existe registro para eliminar");
        cuentaRepo.deleteById(id);
        return cuentaRepo.findById(id).isPresent();
    }

    @Override
    public Cuenta obtener(UUID id) {
        return cuentaRepo.findById(id).orElseThrow(() -> new NoEncontradoExc("No existe registro con el id indicado"));
    }

    @Transactional
    @Override
    public BigDecimal actualizaSaldo(Integer tipoMov, BigDecimal valor, UUID idCuenta) {
        Optional<Cuenta> op = cuentaRepo.findById(idCuenta);
        if(op.isEmpty()) throw new NoEncontradoExc("No existe cuenta para generar el movimiento");
        Cuenta cuenta = op.get();
        BigDecimal disp = cuenta.getSaldoDisponible();
        System.out.println(disp);
        if(valor.compareTo(cuenta.getSaldoDisponible()) == 1 && tipoMov == 2) throw new BadRequestExp("Saldo no disponible");
        BigDecimal saldo = tipoMov == 1 ? disp.add(valor) : disp.subtract(valor);
        System.out.println(saldo);
        Integer modificado = cuentaRepo.actualizaSaldo(saldo, idCuenta);
        if(modificado == 0) throw new BadRequestExp("No se pudo actualizar saldo en cuenta, no se registro movimiento");
        return saldo;
    }

    @Override
    public List<Cuenta> obtenerPorIdCliente(Integer idCliente) {
        return  cuentaRepo.findAllByIdClienteIs(idCliente);
    }
}
