package com.shelfguard.shelfguard.product.dtoproduct;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductDTO {
	
	 @NotNull(message = "Ürün ID boş olamaz")
	    private Long id;
	 
}
