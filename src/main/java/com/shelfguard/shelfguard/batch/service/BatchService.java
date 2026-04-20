package com.shelfguard.shelfguard.batch.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shelfguard.shelfguard.batch.dto.BatchResponseDTO;
import com.shelfguard.shelfguard.batch.dto.CreateBatchDTO;
import com.shelfguard.shelfguard.batch.entity.Batch;
import com.shelfguard.shelfguard.batch.repository.BatchRepository;
import com.shelfguard.shelfguard.common.exception.BusinessErrorCode;
import com.shelfguard.shelfguard.common.exception.BusinessException;
import com.shelfguard.shelfguard.product.entity.Product;
import com.shelfguard.shelfguard.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@Transactional
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;

    public BatchResponseDTO createBatch(CreateBatchDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRODUCT_NOT_FOUND));

        if (dto.getExpiryDate().isBefore(LocalDate.now())) {
            throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        }

        Batch batch = Batch.builder()
                .product(product)
                .quantityReceived(dto.getQuantityReceived())
                .expiryDate(dto.getExpiryDate())
                .build();

        Batch saved = batchRepository.save(batch);

        return mapToDTO(saved);
    }

    public List<BatchResponseDTO> getBatchesByProduct(Long productId) {
        return batchRepository.findByProductIdOrderByExpiryDateAsc(productId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<BatchResponseDTO> getExpiringBatches(int days) {
        LocalDate threshold = LocalDate.now().plusDays(days);

        return batchRepository.findByExpiryDateBeforeOrderByExpiryDateAsc(threshold)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<BatchResponseDTO> getAll() {
        return batchRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private BatchResponseDTO mapToDTO(Batch batch) {
        return BatchResponseDTO.builder()
                .id(batch.getId())
                .productId(batch.getProduct().getId())
                .productName(batch.getProduct().getName())
                .quantityReceived(batch.getQuantityReceived())
                .expiryDate(batch.getExpiryDate())
                .receivedDate(batch.getReceivedDate())
                .build();
    }
}
