package com.shelfguard.shelfguard.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

	@Id
	private Long id;
	
	@Column(name = "barcode")
	private String barcode;
	
	@Column(name = "name")
	private String name;
	
	
}
