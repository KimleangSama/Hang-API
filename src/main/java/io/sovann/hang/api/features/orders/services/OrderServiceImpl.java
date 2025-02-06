package io.sovann.hang.api.features.orders.services;

import io.sovann.hang.api.features.orders.repos.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {
    private final OrderRepository orderRepository;
}
