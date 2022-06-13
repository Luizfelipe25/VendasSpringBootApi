package io.github.luizfelipe25.domain.repository;

import io.github.luizfelipe25.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos  extends JpaRepository<Produto, Integer> {
}
