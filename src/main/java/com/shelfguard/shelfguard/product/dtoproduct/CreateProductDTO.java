package com.shelfguard.shelfguard.product.dtoproduct;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDTO {

	@NotBlank(message = "Barcode boş olamaz")
    private String barcode;

    @NotBlank(message = "Ürün adı boş olamaz")
    private String name;
}
