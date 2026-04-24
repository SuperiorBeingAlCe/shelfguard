package com.shelfguard.shelfguard.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shelfguard.shelfguard.batch.entity.Batch;
import com.shelfguard.shelfguard.batch.repository.BatchRepository;
import com.shelfguard.shelfguard.risk.RiskLevel;
import com.shelfguard.shelfguard.risk.service.RiskService;

@ExtendWith(MockitoExtension.class)
public class RiskServiceTest {
	
	 @Mock
	    private BatchRepository batchRepository;

	    @InjectMocks
	    private RiskService riskService;

	    // 1. CRITICAL test
	    @Test
	    void shouldReturnCriticalRisk_whenExpiryIsNear() {

	        Batch batch = Batch.builder()
	                .expiryDate(LocalDate.now().plusDays(2))
	                .build();

	        RiskLevel risk = riskService.calculateBatchRisk(batch);

	        assertEquals(RiskLevel.CRITICAL, risk);
	    }

	    // 2. HIGH test
	    @Test
	    void shouldReturnHighRisk_whenExpiryBetween4And7Days() {

	        Batch batch = Batch.builder()
	                .expiryDate(LocalDate.now().plusDays(5))
	                .build();

	        RiskLevel risk = riskService.calculateBatchRisk(batch);

	        assertEquals(RiskLevel.HIGH, risk);
	    }

	    // 3. LOW test
	    @Test
	    void shouldReturnLowRisk_whenExpiryFar() {

	        Batch batch = Batch.builder()
	                .expiryDate(LocalDate.now().plusDays(30))
	                .build();

	        RiskLevel risk = riskService.calculateBatchRisk(batch);

	        assertEquals(RiskLevel.LOW, risk);
	    }

	    // 4. risky batch list test
	    @Test
	    void shouldReturnOnlyHighAndCriticalBatches() {

	        Batch critical = Batch.builder()
	                .expiryDate(LocalDate.now().plusDays(1))
	                .build();

	        Batch safe = Batch.builder()
	                .expiryDate(LocalDate.now().plusDays(50))
	                .build();

	        Mockito.when(batchRepository.findAll())
	                .thenReturn(List.of(critical, safe));

	        List<Batch> result = riskService.getAllRiskyBatches();

	        assertEquals(1, result.size());
	    }
}
