package io.github.curso.rest.controller;

import io.github.curso.domain.dto.PedidoDTO;
import io.github.curso.domain.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer cadastraProduto(@RequestBody PedidoDTO pedido){

        return service.cadastrarPedido(pedido);

    }
}