package ru.yandex.practicum.dto.cart;

import lombok.Data;

import java.util.UUID;

@Data
public class ChangeProductQuantityRequest {
    private UUID productId;
    private Integer newQuantity;
}
