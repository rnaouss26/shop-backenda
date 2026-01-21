package rnaouss.cnam.shop.api;

import org.springframework.web.bind.annotation.*;
import rnaouss.cnam.shop.model.CartItem;
import rnaouss.cnam.shop.model.CartItemId;
import rnaouss.cnam.shop.repo.CartItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                    log.info("ADD_ITEM cartId={} productId={} qty={}", cartId, req.productId(), req.quantity());
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
    
    
   

    @PostMapping("/{cartId}/checkout")
    public CheckoutResponse checkout(@PathVariable String cartId) {

        List<CartItem> items = repo.findByCartId(cartId);

        if (items == null || items.isEmpty()) {
            log.warn("CHECKOUT_EMPTY cartId={}", cartId);
            return new CheckoutResponse(null, 0, "Cart is empty");
        }

        String orderId = "ORD-" + System.currentTimeMillis();
        int cleared = items.size();

        log.info("CHECKOUT_START cartId={} orderId={} items={}", cartId, orderId, cleared);

        repo.deleteAll(items);

        log.info("CHECKOUT_DONE cartId={} orderId={} cleared={}", cartId, orderId, cleared);

        return new CheckoutResponse(orderId, cleared, "OK");
    }

    public record CheckoutResponse(String orderId, int itemsCleared, String message) {}
    private static final Logger log = LoggerFactory.getLogger(CartController.class);
 

    

}
