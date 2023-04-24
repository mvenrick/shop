package com.coffee.shop.controllers;


import com.coffee.shop.models.Product;
import com.coffee.shop.repositories.ProductRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Product> getProduct(@PathVariable Long id) {

        return productRepository.findById(id);

    }

    @PostMapping
    public ResponseEntity<Product> createProduct (@RequestBody Product product, UriComponentsBuilder uriComponentsBuilder) {
        Product savedItem = productRepository.save(product);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/products/{id}")
                .buildAndExpand(savedItem.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uriComponents.toUri().toString());
        return new ResponseEntity<>(savedItem, headers, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        if (product.getId() != Long.valueOf(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (productRepository.existsById(Long.valueOf(id))) {
            productRepository.save(product);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }
}
