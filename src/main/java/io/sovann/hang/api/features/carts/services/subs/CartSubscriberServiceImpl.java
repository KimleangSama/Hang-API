package io.sovann.hang.api.features.carts.services.subs;

import io.sovann.hang.api.features.carts.entities.*;
import io.sovann.hang.api.features.carts.payloads.requests.*;
import io.sovann.hang.api.features.carts.repos.*;
import lombok.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class CartSubscriberServiceImpl {
    private final CartRepository cartRepository;

    @Transactional
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveCartMessage(
            CreateCartQRequest request
    ) {
        Cart cart = new Cart();
        cart.setTable(request.getTable());
        cart.setCreatedBy(request.getUserId());
        cartRepository.save(cart);
    }
}
