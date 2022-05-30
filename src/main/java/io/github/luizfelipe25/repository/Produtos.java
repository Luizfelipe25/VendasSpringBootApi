package io.github.luizfelipe25.repository;

import io.github.luizfelipe25.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos  extends JpaRepository<Produto, Integer> {
}
