package io.github.curso.domain.util;

import io.github.curso.domain.dto.GetItensPedidoDTO;
import io.github.curso.domain.dto.GetPedidoDTO;
import io.github.curso.domain.entity.ItemPedido;
import io.github.curso.domain.entity.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TransformerPedidoToPedidoDTO implements Function<Pedido, GetPedidoDTO> {
    @Override
    public GetPedidoDTO apply(Pedido pedido) {

        GetPedidoDTO pedidoDTO = new GetPedidoDTO();
        pedidoDTO.setDataPedido(pedido.getDataPedido());
        pedidoDTO.setCpfCliente(pedido.getCliente().getCpf());
        pedidoDTO.setTotal(pedido.getTotal());
        pedidoDTO.setStatus(pedido.getStatusPedido().name());
        pedidoDTO.setNomeCliente(pedido.getCliente().getNome());
        pedidoDTO.setCodigoPedido(pedido.getId());
        pedidoDTO.setItens(devolveGetItensPedido(pedido.getItens()));

        return pedidoDTO;
    }

    private List<GetItensPedidoDTO> devolveGetItensPedido(List<ItemPedido> itens) {

        return itens.stream().map(itemPedido -> GetItensPedidoDTO.builder()
                                                                 .produto(itemPedido.getProduto().getId())
                                                                 .quantidade(itemPedido.getQuantidade())
                                                                 .descricaoProduto(itemPedido.getProduto().getDescricao())
                                                                 .valorUnitario(itemPedido.getProduto().getPreco())
                                                                 .build())
                                                                 .collect(Collectors.toList());

    }
}
