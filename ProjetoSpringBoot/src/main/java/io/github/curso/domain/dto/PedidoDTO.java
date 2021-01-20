package io.github.curso.domain.dto;

import io.github.curso.validation.ValidaItensPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotNull(message = "{predido.cliente}")
    private Integer cliente;
    @NotNull(message = "{}")
    private BigDecimal total;

    @ValidaItensPedido()
    private List<ItemPedidoDTO> itens;

}
