package rnaouss.cnam.shop.api;

import org.springframework.web.bind.annotation.*;
import rnaouss.cnam.shop.model.Product;
import rnaouss.cnam.shop.repo.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Product one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }
}
