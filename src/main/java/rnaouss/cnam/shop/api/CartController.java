package rnaouss.cnam.shop.api;

import org.springframework.web.bind.annotation.*;
import rnaouss.cnam.shop.model.CartItem;
import rnaouss.cnam.shop.model.CartItemId;
import rnaouss.cnam.shop.repo.CartItemRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartItemRepository repo;

    public CartController(CartItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{cartId}")
    public List<CartItem> getCart(@PathVariable String cartId) {
        return repo.findByCartId(cartId);
    }

    public record AddItemRequest(Long productId, int quantity) {}

    @PostMapping("/{cartId}/items")
    public CartItem addItem(@PathVariable String cartId, @RequestBody AddItemRequest req) {
        int qty = Math.max(req.quantity(), 1);
        CartItemId id = new CartItemId(cartId, req.productId());

        return repo.findById(id)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + qty);
                    return repo.save(existing);
                })
                .orElseGet(() -> repo.save(new CartItem(cartId, req.productId(), qty)));
    }

    public record UpdateQtyRequest(int quantity) {}

    @PutMapping("/{cartId}/items/{productId}")
    public CartItem updateQty(@PathVariable String cartId, @PathVariable Long productId, @RequestBody UpdateQtyRequest req) {
        int qty = Math.max(req.quantity(), 1);
        CartItemId id = new CartItemId(cartId, productId);

        CartItem item = repo.findById(id).orElseThrow();
        item.setQuantity(qty);
        return repo.save(item);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public void remove(@PathVariable String cartId, @PathVariable Long productId) {
        repo.deleteById(new CartItemId(cartId, productId));
    }
}
