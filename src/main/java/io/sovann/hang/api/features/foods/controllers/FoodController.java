package io.sovann.hang.api.features.foods.controllers;

import io.sovann.hang.api.annotations.*;
import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.commons.controllers.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.foods.payloads.requests.*;
import io.sovann.hang.api.features.foods.payloads.responses.*;
import io.sovann.hang.api.features.foods.services.*;
import io.sovann.hang.api.features.users.securities.*;
import io.sovann.hang.api.utils.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(APIURLs.FOOD)
@RequiredArgsConstructor
public class FoodController {
    private final FoodServiceImpl foodService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    public BaseResponse<FoodResponse> createFood(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateFoodRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodService.createFood(user.getUser(), request),
                "Food failed to create",
                null);
    }

    @GetMapping("/list")
    public BaseResponse<List<FoodResponse>> listFoodByCategoryId(
            @CurrentUser CustomUserDetails user,
            @RequestParam UUID categoryId
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodService.listFoodByCategoryId(user.getUser(), categoryId),
                "Food failed to list",
                null);
    }

    @GetMapping("/list/all")
    public BaseResponse<List<FoodResponse>> listFoodByCategoryId(
            @CurrentUser CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        PageMeta meta = new PageMeta(page, size, foodService.count());
        return callback.execute(() -> foodService.listFoods(user.getUser(), page, size),
                "Food failed to list",
                meta);
    }
}
