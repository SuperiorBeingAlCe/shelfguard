package com.shelfguard.shelfguard.risk.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shelfguard.shelfguard.batch.entity.Batch;
import com.shelfguard.shelfguard.batch.repository.BatchRepository;
import com.shelfguard.shelfguard.common.exception.BusinessErrorCode;
import com.shelfguard.shelfguard.common.exception.BusinessException;
import com.shelfguard.shelfguard.risk.RiskLevel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiskService {

	  private final BatchRepository batchRepository;

	  public RiskLevel calculateBatchRisk(Batch batch) {
		  if(batch.getExpiryDate() == null) {
			  throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
		  }
		  
		  long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), batch.getExpiryDate());
		  
		  if(daysLeft <=3) {
			  return RiskLevel.CRITICAL;
			  } else if(daysLeft <=7) {
				  return RiskLevel.HIGH;
				  } else if(daysLeft <=21) {
					  return RiskLevel.MEDIUM;
				  } else {
					  return RiskLevel.LOW;
				  }
	   }
	  
 	  				public List<Batch> getAllRiskyBatches() {
 	  					List<Batch> batches = batchRepository.findAll();
 	  					
 	  					return batches.stream()
 	  							.filter(batch -> {
 	  								RiskLevel risk 	= calculateBatchRisk(batch);
 	  								return risk == RiskLevel.CRITICAL || risk == RiskLevel.HIGH;
 	  							})
 	  							.toList();
 	  				}
 	  				
 	  				public List<Batch> getCriticalBatchesOnly() {

 	  			        return batchRepository.findAll().stream()
 	  			                .filter(batch -> calculateBatchRisk(batch) == RiskLevel.CRITICAL)
 	  			                .toList();
 	  			    }
}
