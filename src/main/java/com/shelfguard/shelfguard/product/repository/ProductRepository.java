package com.shelfguard.shelfguard.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shelfguard.shelfguard.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Optional<Product> findByBarcode(String barcode);
	boolean existsByBarcode(String barcode);
	Optional<Product> findByName(String name);
}
