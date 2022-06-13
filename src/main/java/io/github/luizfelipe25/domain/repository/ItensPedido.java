package io.github.luizfelipe25.domain.repository;

import io.github.luizfelipe25.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {

}
