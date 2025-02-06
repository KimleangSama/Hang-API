package io.sovann.hang.api.features.carts.controllers;

import io.sovann.hang.api.annotations.*;
import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.carts.payloads.requests.*;
import io.sovann.hang.api.features.carts.payloads.responses.*;
import io.sovann.hang.api.features.carts.services.*;
import io.sovann.hang.api.features.commons.controllers.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.users.securities.*;
import io.sovann.hang.api.utils.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIURLs.CART_FOOD)
@RequiredArgsConstructor
public class CartFoodController {
    private final CartFoodServiceImpl cartFoodService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    public BaseResponse<CartFoodResponse> createCart(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateCartFoodRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> cartFoodService.createCart(user.getUser(), request),
                "CartFood failed to create",
                null);
    }

}
