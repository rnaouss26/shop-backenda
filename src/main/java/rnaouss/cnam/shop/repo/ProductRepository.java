package rnaouss.cnam.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rnaouss.cnam.shop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
