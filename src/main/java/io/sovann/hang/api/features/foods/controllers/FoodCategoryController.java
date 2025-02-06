package io.sovann.hang.api.features.foods.controllers;

import io.sovann.hang.api.annotations.*;
import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.commons.controllers.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.foods.payloads.requests.*;
import io.sovann.hang.api.features.foods.payloads.responses.*;
import io.sovann.hang.api.features.foods.services.*;
import io.sovann.hang.api.features.users.entities.*;
import io.sovann.hang.api.features.users.securities.*;
import io.sovann.hang.api.utils.*;
import java.util.*;
import lombok.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<List<FoodCategoryResponse>> listFoodCategories(
            @CurrentUser CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        User authUser = user == null ? null : user.getUser();
        PageMeta pageMeta = new PageMeta(page, size, foodCategoryService.count());
        return callback.execute(() -> foodCategoryService.listFoodCategories(authUser, page, size),
                "Food category failed to list",
                pageMeta);
    }

    @PatchMapping("/toggle-visibility")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<FoodCategoryResponse> toggleFoodCategoryVisibility(
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
    public BaseResponse<FoodCategoryResponse> toggleFoodCategoryAvailability(
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
