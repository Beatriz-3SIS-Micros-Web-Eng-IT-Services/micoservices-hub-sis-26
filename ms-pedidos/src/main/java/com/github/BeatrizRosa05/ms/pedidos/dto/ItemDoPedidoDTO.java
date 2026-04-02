package com.github.BeatrizRosa05.ms.pedidos.dto;

import com.github.BeatrizRosa05.ms.pedidos.entities.ItemDoPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemDoPedidoDTO {

    private Long id;

    @NotNull(message = "Quantidade requerida")
    @Positive(message = "Quantidade deve ser um n´número positivo")
    private Integer quantidade;
    @NotBlank(message = "Descrição requerida")
    private String descricao;
    @NotNull(message = "Preço Unitário é requerido")
    @Positive(message = "O preço unitário deve ser um valor positivo e maior que zero")
    private BigDecimal precoUnitario;

    public ItemDoPedidoDTO(ItemDoPedido itemDoPedido){
        id = itemDoPedido.getId();
        quantidade = itemDoPedido.getQuantidade();
        descricao = itemDoPedido.getDescricao();
        precoUnitario = itemDoPedido.getPrecoUnitario();
    }


}///////////////
