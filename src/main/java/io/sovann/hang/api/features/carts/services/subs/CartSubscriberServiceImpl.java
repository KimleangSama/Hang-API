package io.sovann.hang.api.features.carts.services.subs;

import io.sovann.hang.api.features.carts.entities.*;
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
    public void receiveCartMessage(Cart cart) {
        System.out.println("Received cart message: " + cart);
        cartRepository.save(cart);
    }
}
