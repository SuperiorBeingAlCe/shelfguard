package com.shelfguard.shelfguard.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shelfguard.shelfguard.common.exception.BusinessException;
import com.shelfguard.shelfguard.product.dto.dtoproduct.CreateProductDTO;
import com.shelfguard.shelfguard.product.entity.Product;
import com.shelfguard.shelfguard.product.repository.ProductRepository;
import com.shelfguard.shelfguard.product.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	 @Mock
	    private ProductRepository productRepository;
	 
	 @InjectMocks
	    private ProductService productService;
	 
	 @Test
	    void shouldCreateProductSuccessfully() {

	        // given
	        CreateProductDTO dto = new CreateProductDTO();
	        dto.setBarcode("123");
	        dto.setName("Milk");

	        when(productRepository.existsByBarcode("123")).thenReturn(false);

	        Product savedProduct = Product.builder()
	                .barcode("123")
	                .name("Milk")
	                .build();

	        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

	        // when
	        Product result = productService.createProduct(dto);

	        // then
	        assertEquals("123", result.getBarcode());
	        assertEquals("Milk", result.getName());
	    }
	 
	 @Test
	    void shouldThrowException_whenBarcodeExists() {

	        // given
	        CreateProductDTO dto = new CreateProductDTO();
	        dto.setBarcode("123");
	        dto.setName("Milk");

	        when(productRepository.existsByBarcode("123")).thenReturn(true);

	        // when + then
	        assertThrows(BusinessException.class,
	                () -> productService.createProduct(dto));
	    }

	    @Test
	    void shouldReturnProduct_whenBarcodeExists() {

	        // given
	        Product product = Product.builder()
	                .barcode("123")
	                .name("Milk")
	                .build();

	        when(productRepository.findByBarcode("123"))
	                .thenReturn(Optional.of(product));

	        // when
	        Product result = productService.getByBarcode("123");

	        // then
	        assertEquals("Milk", result.getName());
	    }
}
