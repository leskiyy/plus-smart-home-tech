package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.dto.store.ProductCategory;
import ru.yandex.practicum.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("select p from Product p where p.productCategory = ?1 and p.productState = 'ACTIVE'")
    Page<Product> findAllByProductCategory(ProductCategory category, Pageable pageable);
}
