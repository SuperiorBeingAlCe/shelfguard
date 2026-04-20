package com.shelfguard.shelfguard.batch.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BatchResponseDTO {

    private Long id;
    private Long productId;
    private String productName;
    private Integer quantityReceived;
    private LocalDate expiryDate;
    private LocalDateTime receivedDate;
}
