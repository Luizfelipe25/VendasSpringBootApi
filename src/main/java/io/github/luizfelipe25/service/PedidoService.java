package io.github.luizfelipe25.service;

import io.github.luizfelipe25.domain.Pedido;
import io.github.luizfelipe25.domain.enums.StatusPedido;
import io.github.luizfelipe25.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}

