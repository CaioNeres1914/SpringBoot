package io.github.curso.validation;

import io.github.curso.domain.dto.ItemPedidoDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidaItens implements ConstraintValidator<ValidaItensPedido, List<ItemPedidoDTO>> {


    @Override
    public void initialize(ValidaItensPedido constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<ItemPedidoDTO> value, ConstraintValidatorContext context) {

        return value != null && value.stream().allMatch(v -> v.getQuantidade() != null && v.getProduto() != null);
    }


}
