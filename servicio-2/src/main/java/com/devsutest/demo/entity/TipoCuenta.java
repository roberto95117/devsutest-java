package com.devsutest.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_cuenta")
public class TipoCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cuenta", nullable = false)
    private Integer id;

    @Column(name = "nombre_cuenta", nullable = false, length = 100)
    private String nombreCuenta;

}