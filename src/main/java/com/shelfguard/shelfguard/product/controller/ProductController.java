package com.shelfguard.shelfguard.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfguard.shelfguard.product.dto.dtoproduct.CreateProductDTO;
import com.shelfguard.shelfguard.product.entity.Product;
import com.shelfguard.shelfguard.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	
	private ProductService productService;
	
	 @PostMapping
	    public ResponseEntity<Product> create(@RequestBody CreateProductDTO dto) {
	        Product product = productService.createProduct(dto);
	        return ResponseEntity.ok(product);
	    }
	 
	 @GetMapping("/{barcode}")
	    public ResponseEntity<Product> getByBarcode(@PathVariable String barcode) {
	        Product product = productService.getByBarcode(barcode);
	        return ResponseEntity.ok(product);
	    }
	 
	 @GetMapping
	    public ResponseEntity<List<Product>> getAll() {
	        return ResponseEntity.ok(productService.getAll());
	    }

}
