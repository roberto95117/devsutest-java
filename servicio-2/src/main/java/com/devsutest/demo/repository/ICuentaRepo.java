package com.devsutest.demo.repository;

import com.devsutest.demo.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ICuentaRepo extends JpaRepository<Cuenta, UUID> {

    @Transactional
    @Modifying
    @Query("update Cuenta a SET a.saldoDisponible = :saldo WHERE a.id = :id")
    Integer actualizaSaldo(@Param("saldo") BigDecimal saldo, @Param("id") UUID id);

    List<Cuenta> findAllByIdClienteIs(Integer idCliente);
}