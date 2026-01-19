package rnaouss.cnam.shop.model;

import java.io.Serializable;
import java.util.Objects;

public class CartItemId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cartId;
    private Long productId;

    public CartItemId() {}

    public CartItemId(String cartId, Long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemId)) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(cartId, that.cartId) &&
               Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
