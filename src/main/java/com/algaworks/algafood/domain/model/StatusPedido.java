package com.algaworks.algafood.domain.model;

import lombok.Getter;

import java.util.List;

@Getter
public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    CANCELADO("Cancelado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO);

    private final String descricao;
    private List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = List.of(statusAnteriores);
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }

    public boolean podeAlterarPara(StatusPedido novoStatus) {
        return !naoPodeAlterarPara(novoStatus);
    }
}
