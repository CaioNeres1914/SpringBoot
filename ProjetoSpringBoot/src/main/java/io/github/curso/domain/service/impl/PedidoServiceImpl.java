package io.github.curso.domain.service.impl;

import io.github.curso.domain.dto.ItemPedidoDTO;
import io.github.curso.domain.dto.PedidoDTO;
import io.github.curso.domain.entity.Cliente;
import io.github.curso.domain.entity.ItemPedido;
import io.github.curso.domain.entity.Pedido;
import io.github.curso.domain.entity.Produto;
import io.github.curso.domain.repositorio.Clientes;
import io.github.curso.domain.repositorio.ItemsPedido;
import io.github.curso.domain.repositorio.Pedidos;
import io.github.curso.domain.repositorio.Produtos;
import io.github.curso.domain.service.PedidoService;
import io.github.curso.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedido;

    @Override
    public Integer cadastrarPedido( PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        Cliente cliente = clientesRepository.findById(pedidoDTO.getCliente())
                .orElseThrow(()-> new RegraNegocioException("Codigo de Cliente inexistente !"));
        pedido.setCliente(cliente);
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        List<ItemPedido> itemsPedidos = convertItems(pedido,pedidoDTO.getItens());
        System.out.println("itens" + pedidoDTO.getItens().toString());
        itemsPedido.saveAll(itemsPedidos);
        pedido.setItens(itemsPedidos);
        return repository.save(pedido).getId();

    }

    private List<ItemPedido> convertItems(Pedido pedido, List<ItemPedidoDTO> itens) {

        if (itens.isEmpty())
            new RegraNegocioException("Não há itens para cadastrar pedido");

        return itens.stream()
             .map(itemDto -> {
                 Produto produto = produtosRepository.findById(itemDto.getProduto())
                                                     .orElseThrow(() -> new RegraNegocioException("Produto inexistente: " + itemDto.getProduto()));

                 ItemPedido itemPedido = new ItemPedido();
                 itemPedido.setQuantidade(itemDto.getQuantidade());
                 itemPedido.setPedido(pedido);
                 itemPedido.setProduto(produto);
                 return itemPedido;
             }).collect(Collectors.toList());

    }
}
