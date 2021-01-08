package io.github.curso.domain.repositorio;


import io.github.curso.domain.entity.Cliente;
import io.github.curso.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
