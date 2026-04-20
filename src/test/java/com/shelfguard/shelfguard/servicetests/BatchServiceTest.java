package com.shelfguard.shelfguard.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shelfguard.shelfguard.batch.dto.BatchResponseDTO;
import com.shelfguard.shelfguard.batch.dto.CreateBatchDTO;
import com.shelfguard.shelfguard.batch.entity.Batch;
import com.shelfguard.shelfguard.batch.repository.BatchRepository;
import com.shelfguard.shelfguard.batch.service.BatchService;
import com.shelfguard.shelfguard.common.exception.BusinessException;
import com.shelfguard.shelfguard.product.entity.Product;
import com.shelfguard.shelfguard.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class BatchServiceTest {
	
	 @Mock
	    private BatchRepository batchRepository;

	    @Mock
	    private ProductRepository productRepository;

	    @InjectMocks
	    private BatchService batchService;

	    private Product product;

	    @BeforeEach
	    void setUp() {
	        product = Product.builder()
	                .id(1L)
	                .name("süt")
	                .build();
	    }
	    
	    @Test
	    void shouldCreateBatchSuccessfully() {

	        CreateBatchDTO dto = CreateBatchDTO.builder()
	                .productId(1L)
	                .quantityReceived(10)
	                .expiryDate(LocalDate.now().plusDays(10))
	                .build();

	        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
	        when(batchRepository.save(any(Batch.class)))
	                .thenAnswer(invocation -> invocation.getArgument(0));

	        BatchResponseDTO result = batchService.createBatch(dto);

	        assertNotNull(result);
	        assertEquals(1L, result.getProductId());
	        assertEquals("süt", result.getProductName());

	        verify(batchRepository, times(1)).save(any(Batch.class));
	    }

	    @Test
	    void shouldThrowException_whenProductNotFound() {

	        CreateBatchDTO dto = CreateBatchDTO.builder()
	                .productId(99L)
	                .quantityReceived(10)
	                .expiryDate(LocalDate.now().plusDays(10))
	                .build();

	        when(productRepository.findById(99L)).thenReturn(Optional.empty());

	        assertThrows(BusinessException.class,
	                () -> batchService.createBatch(dto));
	    }
	    
	    @Test
	    void shouldThrowException_whenExpiryDateIsPast() {

	        CreateBatchDTO dto = CreateBatchDTO.builder()
	                .productId(1L)
	                .quantityReceived(10)
	                .expiryDate(LocalDate.now().minusDays(1))
	                .build();

	        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

	        assertThrows(BusinessException.class,
	                () -> batchService.createBatch(dto));
	    }

	    @Test
	    void shouldReturnBatchesByProduct() {

	        Batch batch = Batch.builder()
	                .id(1L)
	                .product(product)
	                .quantityReceived(10)
	                .expiryDate(LocalDate.now().plusDays(5))
	                .build();

	        when(batchRepository.findByProductIdOrderByExpiryDateAsc(1L))
	                .thenReturn(List.of(batch));

	        List<BatchResponseDTO> result = batchService.getBatchesByProduct(1L);

	        assertEquals(1, result.size());
	        assertEquals(1L, result.get(0).getProductId());
	    }

}
