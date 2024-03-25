package com.devsutest.demo.service.imp;

import com.devsutest.demo.dto.ClientePersonaDto;
import com.devsutest.demo.entity.Cliente;
import com.devsutest.demo.entity.Persona;
import com.devsutest.demo.exception.BadRequestExp;
import com.devsutest.demo.model.RespuestaHttp;
import com.devsutest.demo.repository.IClienteRepo;
import com.devsutest.demo.service.IClienteService;
import com.devsutest.demo.service.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service("clienteService")
public class ClienteServiceImp implements IClienteService {


    private IClienteRepo clienteRepo;
    private IPersonaService personaService;

    @Autowired
    public ClienteServiceImp(
            IClienteRepo clienteRepo,
            IPersonaService personaService
    ){
        this.clienteRepo = clienteRepo;
        this.personaService = personaService;
    }

    @Override
    public Cliente insertar(Cliente objeto) {
        return clienteRepo.save(objeto);
    }

    @Override
    public Cliente modificar(Cliente objeto) {
        return clienteRepo.save(objeto);
    }

    @Override
    public boolean eliminar(Integer id) {
        clienteTieneCuenta(id);
        if(clienteRepo.findById(id).isEmpty()) return false;
        clienteRepo.deleteById(id);
        return clienteRepo.findById(id).isEmpty();
    }

    @Override
    public Cliente obtener(Integer id) {
        Optional<Cliente> cliente = clienteRepo.findById(id);
        return cliente.orElse(null);
    }

    @Override
    public Integer actualizaEstado(Integer idCliente, Integer estado) {
        return clienteRepo.modificarEstado(estado, idCliente);
    }

    @Override
    public Cliente creaClientePersona(ClientePersonaDto dto) {
        Persona p = new Persona();
        p.setId(dto.getId());
        p.setNombre(dto.getNombre());
        p.setEdad(dto.getEdad());
        p.setDireccion(dto.getDireccion());
        p.setGenero(dto.getGenero());
        p.setTelefono(dto.getTelefono());

        p = personaService.insertar(p);

        Cliente c = new Cliente();

        c.setIdentificacion(p);
        c.setEstado(dto.getEstado());
        c.setContrasenia(dto.getContrasenia());

        return clienteRepo.save(c);
    }

    void clienteTieneCuenta(Integer idCliente){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(null, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RespuestaHttp> response = restTemplate.exchange("http://localhost:8081/cuentas/cliente/" + idCliente, HttpMethod.GET, requestEntity, RespuestaHttp.class);
        if(response.getStatusCode().value() != 200) throw new BadRequestExp("no se puedo verificar si cliente tiene cuenta asociada");
        List<Map<String, Object>> map = (List<Map<String, Object>>) response.getBody().getDatos();
        if(!map.isEmpty()) throw new BadRequestExp("No se puede eliminar cliente, tiene cuenta asociada");
    }
}
