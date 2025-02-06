package io.sovann.hang.api.features.orders.services;

import io.sovann.hang.api.features.orders.repos.OrderFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFoodServiceImpl {
    private final OrderFoodRepository orderFoodRepository;
}
