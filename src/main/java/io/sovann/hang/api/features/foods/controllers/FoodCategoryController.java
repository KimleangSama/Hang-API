package io.sovann.hang.api.features.foods.controllers;

import io.sovann.hang.api.annotations.CurrentUser;
import io.sovann.hang.api.constants.APIURLs;
import io.sovann.hang.api.features.commons.controllers.ControllerServiceCallback;
import io.sovann.hang.api.features.commons.payloads.BaseResponse;
import io.sovann.hang.api.features.foods.payloads.CreateFoodCategoryRequest;
import io.sovann.hang.api.features.foods.payloads.FoodCategoryResponse;
import io.sovann.hang.api.features.foods.payloads.FoodCategoryToggleRequest;
import io.sovann.hang.api.features.foods.services.FoodCategoryServiceImpl;
import io.sovann.hang.api.features.users.securities.CustomUserDetails;
import io.sovann.hang.api.utils.SoftEntityDeletable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(APIURLs.CATEGORY)
@RequiredArgsConstructor
public class FoodCategoryController {
    private final FoodCategoryServiceImpl foodCategoryService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodCategoryResponse> createFoodCategory(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateFoodCategoryRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodCategoryService.createFoodCategory(user.getUser(), request),
                "Food category failed to create",
                null);
    }

    @GetMapping("/list")
    public BaseResponse<List<FoodCategoryResponse>> listFoodCategory(@RequestParam("storeId") UUID storeId) {
        return callback.execute(() -> foodCategoryService.listFoodCategory(storeId),
                "Food category failed to list",
                null);
    }

    @PatchMapping("/toggle-hidden")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodCategoryResponse> hideFoodCategory(
            @CurrentUser CustomUserDetails user,
            @RequestBody FoodCategoryToggleRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodCategoryService.toggleFoodCategoryVisibility(user.getUser(), request),
                "Food category failed to hide",
                null);
    }

    @PatchMapping("/toggle-availability")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodCategoryResponse> availabilityFoodCategory(
            @CurrentUser CustomUserDetails user,
            @RequestBody FoodCategoryToggleRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodCategoryService.toggleFoodCategoryAvailability(user.getUser(), request),
                "Food category failed to availability",
                null);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodCategoryResponse> deleteFoodCategory(
            @CurrentUser CustomUserDetails user,
            @RequestBody FoodCategoryToggleRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> foodCategoryService.deleteFoodCategory(user.getUser(), request),
                "Food category failed to delete",
                null);
    }
}
