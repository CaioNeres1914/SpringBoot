package io.github.curso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetItensPedidoDTO {

    private Integer produto;
    private Integer quantidade;
    private String descricaoProduto;
    private BigDecimal valorUnitario;


}
