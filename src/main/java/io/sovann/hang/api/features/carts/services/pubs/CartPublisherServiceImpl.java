package io.sovann.hang.api.features.carts.services.pubs;

import io.sovann.hang.api.configs.*;
import io.sovann.hang.api.exceptions.*;
import io.sovann.hang.api.features.carts.entities.*;
import io.sovann.hang.api.features.carts.payloads.requests.*;
import io.sovann.hang.api.features.carts.payloads.responses.*;
import io.sovann.hang.api.features.carts.repos.*;
import io.sovann.hang.api.features.tables.entities.*;
import io.sovann.hang.api.features.tables.repos.*;
import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import lombok.*;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class CartPublisherServiceImpl {
    private final RabbitTemplate rabbitTemplate;
    private final TableRepository tableRepository;
    private final CartFoodRepository cartFoodRepository;

    @Transactional
    public CartResponse createCart(User user, CreateCartRequest request) {
        Table table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", request.getTableId().toString()));
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setTable(table);
        cart.setCreatedBy(user.getCreatedBy());
        rabbitTemplate.convertAndSend(
                CartRabbitMQConfig.QUEUE_NAME,
                cart);
        return CartResponse.fromEntity(cart);
    }
}
