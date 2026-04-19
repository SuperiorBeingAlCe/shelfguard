package com.shelfguard.shelfguard.batch.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shelfguard.shelfguard.batch.entity.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long>{
	
	List<Batch> findByProductId(Long productId);

	List<Batch> findByProductIdOrderByExpiryDateAsc(Long productId);

	List<Batch> findByExpiryDateBefore(LocalDate date);

	List<Batch> findByExpiryDateBeforeOrderByExpiryDateAsc(LocalDate date);

	List<Batch> findByExpiryDateBetween(LocalDate start, LocalDate end);
}
