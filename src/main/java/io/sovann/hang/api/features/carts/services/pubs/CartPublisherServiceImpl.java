package io.sovann.hang.api.features.carts.services.pubs;

import io.sovann.hang.api.configs.*;
import io.sovann.hang.api.exceptions.*;
import io.sovann.hang.api.features.carts.payloads.requests.*;
import io.sovann.hang.api.features.carts.payloads.responses.*;
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

    @Transactional
    public CartResponse createCart(User user, CreateCartRequest request) {
        Table table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", request.getTableId().toString()));
        CreateCartQRequest req = new CreateCartQRequest();
        req.setTable(table);
        req.setUserId(user.getId());
        rabbitTemplate.convertAndSend(
                CartRabbitMQConfig.QUEUE_NAME,
                req);
        CartResponse response = new CartResponse();
        response.setTableId(table.getId());
        return response;
    }
}
