package ru.yandex.practicum.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.client.ShoppingCartClient;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController implements ShoppingCartClient {

    private final ShoppingCartService shoppingCartService;

    @Override
    @GetMapping
    public ShoppingCartDto getCart(@RequestParam @NotBlank String username) {
        log.info("Getting cart by username={}", username);
        return shoppingCartService.getCart(username);
    }

    @Override
    @PutMapping
    public ShoppingCartDto addProductsToCart(@RequestParam @NotBlank String username,
                                             @RequestBody Map<@NotNull UUID, @Positive Integer> products) {
        log.info("Adding products to cart by username={}, products={}", username, products);
        return shoppingCartService.addProductToCart(username, products);
    }

    @Override
    @DeleteMapping
    public void deactivateCart(@RequestParam @NotBlank String username) {
        log.info("Deactivating cart by username={}", username);
        shoppingCartService.deactivateCartByUsername(username);
    }

    @Override
    @PostMapping("/remove")
    public ShoppingCartDto removeProductsFromCart(@RequestParam @NotBlank String username,
                                                  @RequestBody List<@NotNull UUID> productsIds) {
        log.info("Removing products={} from cart username={}", productsIds, username);
        return shoppingCartService.deleteProduct(username, productsIds);
    }

    @Override
    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantity(@RequestParam @NotBlank String username, @RequestBody ChangeProductQuantityRequest request) {
        log.info("Change products quantity={}, username={}", request, username);
        return shoppingCartService.changeProductQuantity(username, request);
    }

}
