package com.devsutest.demo.service;

import com.devsutest.demo.dto.ClientePersonaDto;
import com.devsutest.demo.entity.Cliente;

public interface IClienteService extends ICRUD<Cliente, Integer>  {

    Integer actualizaEstado(Integer idCliente, Integer estado);

    Cliente creaClientePersona(ClientePersonaDto dto);
}
