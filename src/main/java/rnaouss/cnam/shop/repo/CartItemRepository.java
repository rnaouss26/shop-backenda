package rnaouss.cnam.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rnaouss.cnam.shop.model.CartItem;
import rnaouss.cnam.shop.model.CartItemId;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    List<CartItem> findByCartId(String cartId);
}
