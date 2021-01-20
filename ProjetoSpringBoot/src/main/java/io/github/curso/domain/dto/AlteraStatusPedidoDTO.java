package io.github.curso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlteraStatusPedidoDTO {
    @NotBlank(message = "{pedido.status}")
    String statusPedido;

}
