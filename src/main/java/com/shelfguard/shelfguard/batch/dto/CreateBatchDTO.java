package com.shelfguard.shelfguard.batch.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBatchDTO {
	
	  @NotNull
	    private Long productId;

	    @NotNull
	    @Min(1)
	    private Integer quantityReceived;

	    @NotNull
	    @Future
	    private LocalDate expiryDate;
}
