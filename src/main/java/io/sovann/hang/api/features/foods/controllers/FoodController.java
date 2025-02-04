package io.sovann.hang.api.features.foods.controllers;

import io.sovann.hang.api.annotations.CurrentUser;
import io.sovann.hang.api.constants.APIURLs;
import io.sovann.hang.api.features.commons.controllers.ControllerServiceCallback;
import io.sovann.hang.api.features.commons.payloads.BaseResponse;
import io.sovann.hang.api.features.commons.payloads.PageMeta;
import io.sovann.hang.api.features.foods.payloads.requests.CreateFoodRequest;
import io.sovann.hang.api.features.foods.payloads.requests.FoodToggleRequest;
import io.sovann.hang.api.features.foods.payloads.responses.FoodCategoryResponse;
import io.sovann.hang.api.features.foods.payloads.responses.FoodResponse;
import io.sovann.hang.api.features.foods.services.FoodServiceImpl;
import io.sovann.hang.api.features.users.securities.CustomUserDetails;
import io.sovann.hang.api.utils.SoftEntityDeletable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
