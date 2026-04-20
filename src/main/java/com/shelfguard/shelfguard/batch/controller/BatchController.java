package com.shelfguard.shelfguard.batch.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shelfguard.shelfguard.batch.dto.BatchResponseDTO;
import com.shelfguard.shelfguard.batch.dto.CreateBatchDTO;
import com.shelfguard.shelfguard.batch.service.BatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping
    public ResponseEntity<BatchResponseDTO> createBatch(
            @Valid @RequestBody CreateBatchDTO dto) {

        BatchResponseDTO response = batchService.createBatch(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BatchResponseDTO>> getAll() {

        List<BatchResponseDTO> response = batchService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<BatchResponseDTO>> getByProduct(
            @PathVariable Long productId) {

        List<BatchResponseDTO> response = batchService.getBatchesByProduct(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<BatchResponseDTO>> getExpiring(
            @RequestParam(defaultValue = "7") int days) {

        List<BatchResponseDTO> response = batchService.getExpiringBatches(days);
        return ResponseEntity.ok(response);
    }
}