package com.shelfguard.shelfguard.batch.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.shelfguard.shelfguard.product.entity.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Batches")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
	
	 private Integer quantityReceived;
	 
	 private Integer quantityRemaining;
	 
	 private LocalDate expiryDate;

	  private LocalDateTime receivedDate;
	  
	  @PrePersist
	    public void prePersist() {
	        this.receivedDate = LocalDateTime.now();
	        this.quantityRemaining = this.quantityReceived; // 🔥 kritik
	    }
	 
}
