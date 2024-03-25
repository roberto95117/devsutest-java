package com.devsutest.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento", nullable = false)
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne()
    @JoinColumn(name = "id_tipo_movimiento", nullable = false)
    private TipoMovimiento idTipoMovimiento;

    @Column(name = "valor", nullable = false, precision = 16, scale = 2)
    private BigDecimal valor;

    @Column(name = "saldo", nullable = false, precision = 16, scale = 2)
    private BigDecimal saldo;

    @ManyToOne()
    @JoinColumn(name = "numero_cuenta", nullable = false)
    private Cuenta numeroCuenta;

}