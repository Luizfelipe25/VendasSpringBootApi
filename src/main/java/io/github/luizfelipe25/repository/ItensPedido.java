package io.github.luizfelipe25.repository;

import io.github.luizfelipe25.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {

}
