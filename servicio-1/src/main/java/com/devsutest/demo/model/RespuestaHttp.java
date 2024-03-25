package com.devsutest.demo.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RespuestaHttp {
    private String msj;
    private Object datos;
    private Integer code;
}
