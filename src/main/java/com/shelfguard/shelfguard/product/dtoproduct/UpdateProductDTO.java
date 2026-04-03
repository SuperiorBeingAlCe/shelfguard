package com.shelfguard.shelfguard.product.dtoproduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductDTO {
	
	@NotNull(message = "Ürün ID boş olamaz")
    private Long id;

    @NotBlank(message = "Barcode boş olamaz")
    private String barcode;

    @NotBlank(message = "Ürün adı boş olamaz")
    private String name;
}
