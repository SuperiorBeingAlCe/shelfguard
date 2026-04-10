package com.shelfguard.shelfguard.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shelfguard.shelfguard.common.exception.BusinessErrorCode;
import com.shelfguard.shelfguard.common.exception.BusinessException;
import com.shelfguard.shelfguard.product.dto.dtoproduct.CreateProductDTO;
import com.shelfguard.shelfguard.product.entity.Product;
import com.shelfguard.shelfguard.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	
	public Product createProduct(CreateProductDTO dto) {
		
		if(productRepository.existsByBarcode(dto.getBarcode())) {
            throw new BusinessException(BusinessErrorCode.PRODUCT_ALREADY_EXISTS);
        }
		if (dto.getBarcode() == null || dto.getBarcode().isBlank()) {
		    throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
		}
		
		Product product = Product.builder()
				.barcode(dto.getBarcode())
				 .name(dto.getName())
		            .build();

		    return productRepository.save(product);
		}
	
	public Product getByBarcode(String barcode) {
	    return productRepository.findByBarcode(barcode)
	            .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRODUCT_NOT_FOUND));
	}
	public List<Product> getAll() {
	    return productRepository.findAll();
	}
	
	
	
}
