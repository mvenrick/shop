package com.coffee.shop.repositories;

import com.coffee.shop.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
