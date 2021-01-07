package io.github.curso.rest.controller;

import io.github.curso.domain.entity.Cliente;
import io.github.curso.domain.repositorio.Clientes;
import io.github.curso.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/")
public class ClienteController {

    @Autowired
    private ClienteService clienteServices;

    @Autowired
    private Clientes clienteRepository;

    @GetMapping("{id}")
    public Cliente getClienteById( @PathVariable Integer id ){
        return clienteRepository.findById(id)
                                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criaCliente( @RequestBody Cliente cliente){

        return clienteServices.saveCliente(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete (@PathVariable Integer id){

        clienteRepository.findById(id)
                .map( cliente -> {
                    clienteRepository.delete(cliente );
                    return cliente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não existe"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update (@PathVariable Integer id, @RequestBody Cliente cliente) {

        clienteRepository.findById(id)
                         .map(clienteExistente -> {cliente.setId(clienteExistente.getId());
                         clienteRepository.save(cliente);
                         return clienteExistente;
                         }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não existe"));

    }

    @GetMapping
    public List<Cliente> findall ( Cliente cliente) {

        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withIgnoreCase()
                                               .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(cliente,matcher);

        return clienteRepository.findAll(example);

    }

}
