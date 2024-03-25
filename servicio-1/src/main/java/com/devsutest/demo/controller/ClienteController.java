package com.devsutest.demo.controller;


import com.devsutest.demo.dto.ClientePersonaDto;
import com.devsutest.demo.entity.Cliente;
import com.devsutest.demo.exception.BadRequestExp;
import com.devsutest.demo.exception.NoEncontradoExc;
import com.devsutest.demo.model.RespuestaHttp;
import com.devsutest.demo.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private IClienteService clienteService;

    @Autowired
    public ClienteController(
            IClienteService clienteService
    ){
        this.clienteService = clienteService;
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaHttp> agregar(@RequestBody ClientePersonaDto clientePersona){
        Cliente cliente1 = clienteService.creaClientePersona(clientePersona);
        RespuestaHttp res= new RespuestaHttp();
        if(cliente1 == null){
            res.setCode(500);
            res.setMsj("Error al crear cliente");
        }else{
            res.setCode(200);
            res.setMsj("Cliente creado");
            res.setDatos(cliente1);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<RespuestaHttp> info(@PathVariable("idCliente") Integer idCliente){
        RespuestaHttp res= new RespuestaHttp();
        Cliente cliente = clienteService.obtener(idCliente);

        if (cliente == null){
            throw new NoEncontradoExc("No existe informacion de cliente con id " + idCliente);
        }

        res.setCode(200);
        res.setDatos(cliente);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<RespuestaHttp> elimina(@PathVariable("idCliente") Integer idCliente){
        RespuestaHttp res= new RespuestaHttp();
        if(!clienteService.eliminar(idCliente)){
            res.setCode(400);
            res.setMsj("No se pudo eliminar registro, verifique id");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        res.setCode(200);
        res.setMsj("Registro eliminado");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<RespuestaHttp> actualiza(@RequestBody Cliente cliente){
        RespuestaHttp res= new RespuestaHttp();
        Cliente cliente1 = clienteService.obtener(cliente.getId());
        if (cliente1 == null){
            throw new NoEncontradoExc("No existe informacion de cliente con id " + cliente.getId());
        }
        clienteService.modificar(cliente);
        res.setCode(200);
        res.setMsj("Registro modificado");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<RespuestaHttp> actualizaEstado(@RequestParam("idCliente") Integer idCliente, @RequestParam("estado") Integer estado){
        RespuestaHttp res= new RespuestaHttp();
        Cliente cliente = clienteService.obtener(idCliente);
        Integer upd = clienteService.actualizaEstado(idCliente, estado);
        if (cliente == null){
            throw new NoEncontradoExc("No existe informacion de cliente con id " + idCliente);
        }else if( upd== 0){
            throw new BadRequestExp("No se pudo actualizar cliente con id "+ idCliente);
        }else{
            res.setCode(200);
            res.setMsj("Se actualizo estado");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
