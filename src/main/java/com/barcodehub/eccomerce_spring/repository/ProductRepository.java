package com.barcodehub.eccomerce_spring.repository;

import com.barcodehub.eccomerce_spring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}