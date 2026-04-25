package com.shelfguard.shelfguard.checklist.dto;

import java.time.LocalDate;

import com.shelfguard.shelfguard.risk.RiskLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyCheckItem {
	
	private String productName;
    private LocalDate expiryDate;
    private long daysToExpiry;
    private RiskLevel riskLevel;
    private String reason;
    
    
}
