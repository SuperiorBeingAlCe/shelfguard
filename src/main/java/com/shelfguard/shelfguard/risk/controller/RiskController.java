package com.shelfguard.shelfguard.risk.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfguard.shelfguard.batch.entity.Batch;
import com.shelfguard.shelfguard.risk.service.RiskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/risk")
@RequiredArgsConstructor
public class RiskController {

	private final RiskService riskService;
	
	@GetMapping("/batches")
	public ResponseEntity<List<Batch>> getRiskyBatches() {
		return ResponseEntity.ok(riskService.getAllRiskyBatches());
	}
}
