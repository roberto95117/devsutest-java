package com.devsutest.demo.service;

public interface ICRUD <T,V>{
    T insertar(T objeto);
    T modificar(T objeto);
    boolean eliminar(V id);

    T obtener(V id);
}
