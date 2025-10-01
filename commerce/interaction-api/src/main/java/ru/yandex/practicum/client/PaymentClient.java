package ru.yandex.practicum.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

import java.util.UUID;

@FeignClient(name = "payment-service", path = "api/v1/payment")
public interface PaymentClient {
    @PostMapping
    PaymentDto createPayment(@RequestBody OrderDto orderDto) throws FeignException;

    @PostMapping("/totalCost")
    Double getTotalCost(@RequestBody OrderDto orderDto) throws FeignException;

    @PostMapping("/refund")
    PaymentDto successPay(@RequestBody UUID payId) throws FeignException;

    @PostMapping("/productCost")
    Double getProductCost(@RequestBody OrderDto orderDto) throws FeignException;

    @PostMapping("/failed")
    PaymentDto failedPay(@RequestBody UUID payId) throws FeignException;
}
