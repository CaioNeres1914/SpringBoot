package io.github.curso.domain.service.impl;

import io.github.curso.domain.dto.AlteraStatusPedidoDTO;
import io.github.curso.domain.dto.GetPedidoDTO;
import io.github.curso.domain.dto.ItemPedidoDTO;
import io.github.curso.domain.dto.PedidoDTO;
import io.github.curso.domain.entity.*;
import io.github.curso.domain.repositorio.Clientes;
import io.github.curso.domain.repositorio.ItemsPedido;
import io.github.curso.domain.repositorio.Pedidos;
import io.github.curso.domain.repositorio.Produtos;
import io.github.curso.domain.service.PedidoService;
import io.github.curso.domain.util.TransformerPedidoToPedidoDTO;
import io.github.curso.exception.NotFoundApplicationException;
import io.github.curso.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedido;
    private final TransformerPedidoToPedidoDTO map;

    @Transactional
    @Override
    public Integer cadastrarPedido( PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        Cliente cliente = clientesRepository.findById(pedidoDTO.getCliente())
                .orElseThrow(()-> new RegraNegocioException("Codigo de Cliente inexistente !"));
        pedido.setCliente(cliente);
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        List<ItemPedido> itemsPedidos = convertItems(pedido,pedidoDTO.getItens());
        System.out.println("itens" + pedidoDTO.getItens().toString());
        itemsPedido.saveAll(itemsPedidos);
        pedido.setItens(itemsPedidos);
        return repository.save(pedido).getId();

    }

    @Override
    public List<GetPedidoDTO> getPedidosDetalhe(Pedido pedido) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                                                      .withIgnoreCase()
                                                      .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(pedido,exampleMatcher);

        List <Pedido> pedidos = repository.findAll(example);
        ArrayList<GetPedidoDTO> getPedidos = new ArrayList();

        return pedidos.stream().map(i -> map.apply(i)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStatus(Integer idPedido, AlteraStatusPedidoDTO status) {

        Pedido pedido = repository.findById(idPedido)
                                  .map(p -> {
                                      p.setStatusPedido(StatusPedido.valueOf(status.getStatusPedido()));
                                      repository.saveAndFlush(p);
                                      return p;
                                  })
                                  .orElseThrow(()-> new NotFoundApplicationException("Não há pedido para ser alterado com esse id! " ));

    }


    private List<ItemPedido> convertItems(Pedido pedido, List<ItemPedidoDTO> itens) {

        if (itens.isEmpty())
            new RegraNegocioException("Não há itens para cadastrar pedido");

        return itens.stream()
             .map(itemDto -> {
                 Produto produto = produtosRepository.findById(itemDto.getProduto())
                                                     .orElseThrow(() -> new NotFoundApplicationException("Produto inexistente: " + itemDto.getProduto()));

                 ItemPedido itemPedido = new ItemPedido();
                 itemPedido.setQuantidade(itemDto.getQuantidade());
                 itemPedido.setPedido(pedido);
                 itemPedido.setProduto(produto);
                 return itemPedido;
             }).collect(Collectors.toList());

    }
}
