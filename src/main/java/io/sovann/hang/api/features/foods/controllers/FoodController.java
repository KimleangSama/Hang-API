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
import org.springframework.security.access.prepost.*;
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
        return callback.execute(() -> foodService.listFoodByCategoryId(user != null ? user.getUser() : null, categoryId),
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

    @PatchMapping("/toggle-visibility")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodResponse> toggleFoodVisibility(
            @CurrentUser CustomUserDetails user,
            @RequestBody FoodToggleRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodService.toggleFoodVisibility(user.getUser(), request),
                "Food failed to set hide",
                null);
    }

    @PatchMapping("/toggle-availability")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodResponse> toggleFoodAvailability(
            @CurrentUser CustomUserDetails user,
            @RequestBody FoodToggleRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodService.toggleFoodAvailability(user.getUser(), request),
                "Food failed to set availability",
                null);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodResponse> deleteFood(
            @CurrentUser CustomUserDetails user,
            @RequestBody FoodToggleRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodService.deleteFood(user.getUser(), request),
                "Food failed to delete",
                null);
    }
}
