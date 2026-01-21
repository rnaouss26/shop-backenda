package rnaouss.cnam.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rnaouss.cnam.shop.model.Product;
import rnaouss.cnam.shop.repo.ProductRepository;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
               repo.save(new Product("T-shirt", new BigDecimal("15.00"), "https://img.rnaouss.org/products/Tshirt.JPG"));
                repo.save(new Product("Sneakers", new BigDecimal("60.00"), "https://img.rnaouss.org/products/Sneakers.JPG"));
                repo.save(new Product("Backpack", new BigDecimal("35.00"), "https://img.rnaouss.org/products/Backpack.JPG"));
            }
        };
    }
}

