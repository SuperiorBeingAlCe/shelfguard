package com.shelfguard.shelfguard.checklist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfguard.shelfguard.checklist.dto.DailyCheckItem;
import com.shelfguard.shelfguard.checklist.service.ChecklistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChecklistController {
	
	 private final ChecklistService checklistService;
	 

	    @GetMapping("/checklist/today")
	    public ResponseEntity<List<DailyCheckItem>> getTodayChecklist() {
	        List<DailyCheckItem> response = checklistService.generateTodayChecklist();
	        return ResponseEntity.ok(response);
	    }
}
