package io.sovann.hang.api.features.foods.payloads.responses;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class FavoriteResponse {
    private UUID foodId;
    private UUID userId;
    private String name;
    private String username;

    public static FavoriteResponse fromEntity(Favorite favorite) {
        FavoriteResponse response = new FavoriteResponse();
        response.setFoodId(favorite.getFood().getId());
        response.setUserId(favorite.getUser().getId());
        response.setName(favorite.getFood().getName());
        response.setUsername(favorite.getUser().getUsername());
        return response;
    }

    public static List<FavoriteResponse> fromEntities(List<Favorite> favorites) {
        return favorites.stream().map(FavoriteResponse::fromEntity).toList();
    }
}
