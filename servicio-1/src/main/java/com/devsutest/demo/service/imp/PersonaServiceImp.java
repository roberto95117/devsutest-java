package com.devsutest.demo.service.imp;

import com.devsutest.demo.entity.Persona;
import com.devsutest.demo.repository.IPersonaRepo;
import com.devsutest.demo.service.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("personaService")
public class PersonaServiceImp implements IPersonaService {

    private IPersonaRepo personaRepo;

    @Autowired
    public PersonaServiceImp(
            IPersonaRepo iPersonaRepo
    ){
        this.personaRepo = iPersonaRepo;
    }
    @Override
    public Persona insertar(Persona objeto) {
        return personaRepo.save(objeto);
    }

    @Override
    public Persona modificar(Persona objeto) {
        return personaRepo.save(objeto);
    }

    @Override
    public boolean eliminar(Long id) {
        if(personaRepo.findById(id).isEmpty()) return false;
        personaRepo.deleteById(id);
        return personaRepo.findById(id).isEmpty();
    }

    @Override
    public Persona obtener(Long id) {
        Optional<Persona> per = personaRepo.findById(id);
        return  per.orElse(null);
    }
}
