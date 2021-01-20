package io.github.curso.rest.controller;

import io.github.curso.domain.dto.AlteraStatusPedidoDTO;
import io.github.curso.domain.dto.GetPedidoDTO;
import io.github.curso.domain.dto.PedidoDTO;
import io.github.curso.domain.entity.Pedido;
import io.github.curso.domain.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos/")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer cadastraProduto(@Valid @RequestBody PedidoDTO pedido){

        return service.cadastrarPedido(pedido);

    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus (@PathVariable Integer id,
                              @RequestBody AlteraStatusPedidoDTO status){

        service.updateStatus(id,status);
    }


    @GetMapping
    public List<GetPedidoDTO> getPedidoDTO (Pedido pedido){

        return service.getPedidosDetalhe(pedido);
    }
}