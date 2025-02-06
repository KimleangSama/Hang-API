package io.sovann.hang.api.features.carts.controllers;

import io.sovann.hang.api.constants.APIURLs;
import io.sovann.hang.api.features.carts.payloads.requests.CartFoodMutateRequest;
import io.sovann.hang.api.features.carts.payloads.requests.CreateCartFoodRequest;
import io.sovann.hang.api.features.carts.payloads.responses.CartFoodResponse;
import io.sovann.hang.api.features.carts.payloads.responses.CartResponse;
import io.sovann.hang.api.features.carts.services.CartFoodServiceImpl;
import io.sovann.hang.api.features.commons.controllers.ControllerServiceCallback;
import io.sovann.hang.api.features.commons.payloads.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(APIURLs.CART_FOOD)
@RequiredArgsConstructor
public class CartFoodController {
    private final CartFoodServiceImpl cartFoodService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    public BaseResponse<CartFoodResponse> createCart(
            @RequestBody CreateCartFoodRequest request
    ) {
        return callback.execute(() -> cartFoodService.createCart(request),
                "CartFood failed to create",
                null);
    }

    @GetMapping("/of-cart/{cartId}/list")
    public BaseResponse<List<CartFoodResponse>> getCartFoodOfCart(
            @PathVariable UUID cartId
    ) {
        return callback.execute(() -> cartFoodService.getCartFoodOfCart(cartId),
                "CartFood failed to get",
                null);
    }

    @GetMapping("/of-table/{tableId}/list")
    public BaseResponse<List<CartFoodResponse>> getCartFoodOfTable(
            @PathVariable UUID tableId
    ) {
        return callback.execute(() -> cartFoodService.getCartFoodOfTable(tableId),
                "CartFood failed to get",
                null);
    }

    @DeleteMapping("/delete")
    public BaseResponse<CartFoodResponse> deleteCartFood(
            @RequestBody CartFoodMutateRequest request
    ) {
        return callback.execute(() -> cartFoodService.deleteCartFood(request),
                "CartFood failed to delete",
                null);
    }

    @PostMapping("/get")
    public BaseResponse<CartFoodResponse> getCartFoodById(
            @RequestBody CartFoodMutateRequest request
    ) {
        return callback.execute(() -> cartFoodService.getCartFoodById(request),
                "Cart failed to get",
                null);
    }

    @PatchMapping("/update")
    public BaseResponse<CartFoodResponse> updateCartFood(
            @RequestBody CartFoodMutateRequest request
    ) {
        return callback.execute(() -> cartFoodService.updateCartFood(request),
                "CartFood failed to update",
                null);
    }
}
