package com.devsutest.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "numero_cuenta", nullable = false)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "id_tipo_cuenta", nullable = false, updatable = false)
    private TipoCuenta idTipoCuenta;

    @Column(name = "saldo_inicial", nullable = false, precision = 16, scale = 2)
    private BigDecimal saldoInicial;

    @Column(name = "saldo_disponible", nullable = false, precision = 16, scale = 2)
    private BigDecimal saldoDisponible;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "id_cliente", nullable = false, updatable = false)
    private Integer idCliente;

}