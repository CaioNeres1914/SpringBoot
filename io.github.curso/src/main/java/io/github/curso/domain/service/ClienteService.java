package io.github.curso.domain.service;

import io.github.curso.domain.entity.Cliente;
import io.github.curso.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private Clientes clienteRepository;

    public Optional<Cliente> getCliente(Integer idCliente) {

        return clienteRepository.findById(idCliente);
    }

    public Cliente saveCliente(Cliente cliente) {

        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Integer idCliente) {

        clienteRepository.deleteById(idCliente);
    }
}
