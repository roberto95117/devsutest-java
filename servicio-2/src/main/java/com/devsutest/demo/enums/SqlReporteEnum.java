package com.devsutest.demo.enums;

public enum SqlReporteEnum {
    DETALLE_MOVIMIENTO_CLIENTE_FECHA(
    """
            SELECT mov.fecha, pe.nombre nombre_cliente, cu.numero_cuenta, tcu.nombre_cuenta tipo_cuenta,
            	cu.saldo_inicial, CASE WHEN cu.estado = 1 THEN 'ACTIVA' ELSE 'INACTIVA' END estado_cuenta,
            	mov.valor valor_movimiento, tmov.nombre tipo_movimiento,
            	mov.saldo
            FROM movimiento mov
            INNER JOIN tipo_movimiento tmov ON tmov.id_tipo_movimiento = mov.id_tipo_movimiento
            INNER JOIN cuenta cu ON cu.numero_cuenta = mov.numero_cuenta
            INNER JOIN tipo_cuenta tcu ON tcu.id_tipo_cuenta = cu.id_tipo_cuenta
            INNER JOIN cliente cl ON cl.id_cliente = cu.id_cliente
            INNER JOIN persona pe ON pe.identificacion = cl.identificacion
            WHERE cl.id_cliente = ?
            AND mov.fecha BETWEEN to_date(?, 'DD/MM/YYYY') AND to_date(?, 'DD/MM/YYYY')
            ORDER BY mov.id_movimiento ASC
            """
    );

    SqlReporteEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
