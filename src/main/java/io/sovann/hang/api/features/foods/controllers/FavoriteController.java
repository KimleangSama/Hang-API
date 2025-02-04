package io.sovann.hang.api.features.foods.controllers;

import io.sovann.hang.api.annotations.CurrentUser;
import io.sovann.hang.api.constants.APIURLs;
import io.sovann.hang.api.features.commons.controllers.ControllerServiceCallback;
import io.sovann.hang.api.features.commons.payloads.BaseResponse;
import io.sovann.hang.api.features.foods.payloads.requests.CreateFavoriteRequest;
import io.sovann.hang.api.features.foods.payloads.responses.FavoriteResponse;
import io.sovann.hang.api.features.foods.services.FoodFavoriteServiceImpl;
import io.sovann.hang.api.features.users.securities.CustomUserDetails;
import io.sovann.hang.api.utils.SoftEntityDeletable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIURLs.FAVORITE)
@RequiredArgsConstructor
public class FavoriteController {
    private final FoodFavoriteServiceImpl favoriteService;
    private final ControllerServiceCallback callback;

    @PostMapping("/favorite")
    @PreAuthorize("authenticated")
    public BaseResponse<FavoriteResponse> createFoodFavorite(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateFavoriteRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> favoriteService.createFavorite(user.getUser(), request),
                "Food category failed to create",
                null);
    }

    @PostMapping("/unfavorite")
    @PreAuthorize("authenticated")
    public BaseResponse<FavoriteResponse> deleteFoodFavorite(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateFavoriteRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> favoriteService.deleteFavorite(user.getUser(), request),
                "Food category failed to delete",
                null);
    }

    @GetMapping("/list")
    @PreAuthorize("authenticated")
    public BaseResponse<List<FavoriteResponse>> listFoodFavorites(
            @CurrentUser CustomUserDetails user
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> favoriteService.listFoodFavorites(user.getUser()),
                "Food category failed to list",
                null);
    }
}
