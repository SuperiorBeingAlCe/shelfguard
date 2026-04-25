package com.shelfguard.shelfguard.checklist.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shelfguard.shelfguard.batch.entity.Batch;
import com.shelfguard.shelfguard.checklist.dto.DailyCheckItem;
import com.shelfguard.shelfguard.risk.RiskLevel;
import com.shelfguard.shelfguard.risk.service.RiskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistService {

	private final RiskService riskService;
	
	public List<DailyCheckItem> generateTodayChecklist() {
		
		return riskService.getAllRiskyBatches()
				.stream()
				.map(this::toChecklistItem)
				.sorted(Comparator.comparing(DailyCheckItem::getDaysToExpiry))
				.toList();
	}
	
	private DailyCheckItem toChecklistItem(Batch batch) {
		long days = ChronoUnit.DAYS.between(
				LocalDate.now(),
				batch.getExpiryDate()
				);
		RiskLevel risk = riskService.calculateBatchRisk(batch);
		
		return DailyCheckItem.builder()
				.productName(batch.getProduct().getName())
				.expiryDate(batch.getExpiryDate())
				.daysToExpiry(days)
				.riskLevel(risk)
				.reason(generateReason(days, risk))
				.build();
	}
	
	private String generateReason(long days, RiskLevel risk) {
		if(risk == RiskLevel.CRITICAL) {
			return "Kritik Risk";
		}
		if(risk == RiskLevel.HIGH) {
			return "Yuksek Risk";
		}
		return "Takip edilmeli";
	}
}
