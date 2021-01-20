package io.github.curso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPedidoDTO {

    private Integer codigoPedido;
    private String status;
    private String cpfCliente;
    private String nomeCliente;
    private BigDecimal total;
    private LocalDate dataPedido;
    private List<GetItensPedidoDTO> itens;
}
