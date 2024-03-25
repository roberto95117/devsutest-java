package com.devsutest.demo.controller;

import com.devsutest.demo.entity.Persona;
import com.devsutest.demo.model.RespuestaHttp;
import com.devsutest.demo.service.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private IPersonaService personaService;

    @Autowired
    public PersonaController(
          IPersonaService personaService
    ){
        this.personaService = personaService;
    }

    @PostMapping
    public ResponseEntity<RespuestaHttp> agregar(@RequestBody Persona persona){
        Persona persona1 = personaService.insertar(persona);
        RespuestaHttp res= new RespuestaHttp();
        if(persona1 == null){
            res.setCode(500);
            res.setMsj("Error al crear persona");
        }else{
            res.setCode(200);
            res.setMsj("persona creada");
            res.setDatos(persona1);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
