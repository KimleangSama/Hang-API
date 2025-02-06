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
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

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
