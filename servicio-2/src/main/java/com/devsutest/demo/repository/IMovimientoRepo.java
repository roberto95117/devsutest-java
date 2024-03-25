package com.devsutest.demo.repository;

import com.devsutest.demo.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IMovimientoRepo extends JpaRepository<Movimiento, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE movimiento SET fecha = to_date(:fecha,'DD-MM-YYYY') WHERE id_movimiento = :id", nativeQuery = true)
    Integer actualizaFecha(@Param("fecha") String fecha, @Param("id") Integer id);
}