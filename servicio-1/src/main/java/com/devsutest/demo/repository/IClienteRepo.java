package com.devsutest.demo.repository;

import com.devsutest.demo.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IClienteRepo extends JpaRepository<Cliente, Integer> {

    @Transactional
    @Modifying
    @Query("Update Cliente Set estado = :estado WHERE id = :idCliente")
    Integer modificarEstado(@Param("estado") Integer estado, @Param("idCliente") Integer idCliente);
}
