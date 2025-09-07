package ru.yandex.practicum.dto.cart;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class ShoppingCartDto {
    private UUID shoppingCartId;
    private Map<UUID, Integer> products;
}
