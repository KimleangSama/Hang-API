package io.sovann.hang.api.features.carts.controllers;

import io.sovann.hang.api.annotations.*;
import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.carts.payloads.requests.*;
import io.sovann.hang.api.features.carts.payloads.responses.*;
import io.sovann.hang.api.features.carts.services.pubs.*;
import io.sovann.hang.api.features.commons.controllers.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.users.securities.*;
import io.sovann.hang.api.utils.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIURLs.CART)
@RequiredArgsConstructor
public class CartController {
    private final CartPublisherServiceImpl cartService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    public BaseResponse<CartResponse> createCart(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateCartRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> cartService.createCart(user.getUser(), request),
                "Cart failed to create",
                null);
    }
}
