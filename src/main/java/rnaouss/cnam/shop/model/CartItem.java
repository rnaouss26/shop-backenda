package rnaouss.cnam.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
@IdClass(CartItemId.class)
public class CartItem {

    @Id
    private String cartId;

    @Id
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    public CartItem() {}

    public CartItem(String cartId, Long productId, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getCartId() { return cartId; }
    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }

    public void setCartId(String cartId) { this.cartId = cartId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
