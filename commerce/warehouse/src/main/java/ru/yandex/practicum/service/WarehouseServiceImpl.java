package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.entity.WarehouseProduct;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.mapper.WarehouseProductMapper;
import ru.yandex.practicum.repository.WarehouseProductRepository;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseProductMapper mapper;

    @Override
    public void addNewProductToWarehouse(NewProductInWarehouseRequest request) {
        if (warehouseProductRepository.existsById(request.getProductId())) {
            throw new NoSpecifiedProductInWarehouseException(400, "Product id=" + request.getProductId() + "has already booked");
        }
        warehouseProductRepository.save(mapper.toEntity(request));
    }

    @Override
    public BookedProductsDto checkWarehouse(ShoppingCartDto shoppingCartDto) {
        Map<UUID, Integer> cartProducts = shoppingCartDto.getProducts();
        Map<UUID, WarehouseProduct> products = warehouseProductRepository.findAllById(cartProducts.keySet())
                .stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, Function.identity()));
        if (products.size() != cartProducts.size()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse(400, "Some product from cart are unavailable");
        }
        double weight = 0;
        double volume = 0;
        boolean fragile = false;

        for (Map.Entry<UUID, Integer> cartProduct : cartProducts.entrySet()) {
            WarehouseProduct product = products.get(cartProduct.getKey());
            if (cartProduct.getValue() > product.getQuantity()) {
                throw new ProductInShoppingCartLowQuantityInWarehouse(
                        400, "Not enough product id=" + cartProduct.getKey() + "in warehouse");
            }
            weight += product.getWeight() * cartProduct.getValue();
            volume += product.getHeight() * product.getWeight() * product.getDepth() * cartProduct.getValue();
            fragile = fragile || product.getFragile();
        }

        return BookedProductsDto.builder()
                .deliveryWeight(weight)
                .deliveryVolume(volume)
                .fragile(fragile)
                .build();
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        WarehouseProduct warehouseProduct = warehouseProductRepository.findById(request.getProductId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException(400, "No info about product id=" + request.getProductId()));

        warehouseProduct.setQuantity(request.getQuantity());
        warehouseProductRepository.save(warehouseProduct);
    }

    @Override
    public AddressDto getAddress() {
        String currentAddress = AddressDto.getCurrentAddress();

        return AddressDto.builder()
                .country(currentAddress)
                .city(currentAddress)
                .street(currentAddress)
                .house(currentAddress)
                .flat(currentAddress)
                .build();
    }
}
