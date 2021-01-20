package io.github.curso.domain.service;

import io.github.curso.domain.dto.AlteraStatusPedidoDTO;
import io.github.curso.domain.dto.GetPedidoDTO;
import io.github.curso.domain.dto.PedidoDTO;
import io.github.curso.domain.entity.Pedido;

import java.util.List;

public interface PedidoService {

     Integer cadastrarPedido(PedidoDTO produto);

     List<GetPedidoDTO> getPedidosDetalhe(Pedido pedido);

     void updateStatus(Integer idPedido, AlteraStatusPedidoDTO status);
}
