package ru.yandex.practicum.dto.store;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {
    private UUID productId;
    @NotNull
    private String productName;
    @NotNull
    private String description;
    @NotNull
    private String imageSrc;
    @NotNull
    private QuantityState quantityState;
    @NotNull
    private ProductState productState;
    private ProductCategory productCategory;
    @NotNull
    @DecimalMin("1.0")
    private Double price;

}
