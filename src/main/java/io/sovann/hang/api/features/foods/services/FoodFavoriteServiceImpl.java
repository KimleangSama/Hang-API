package io.sovann.hang.api.features.foods.services;

import io.sovann.hang.api.exceptions.*;
import io.sovann.hang.api.features.foods.entities.*;
import io.sovann.hang.api.features.foods.payloads.requests.*;
import io.sovann.hang.api.features.foods.payloads.responses.*;
import io.sovann.hang.api.features.foods.repos.*;
import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class FoodFavoriteServiceImpl {
    private final FoodRepository foodRepository;
    private final FoodFavoriteRepository favoriteRepository;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "favorites", key = "#user.id"),
            @CacheEvict(value = "foods", key = "#request.categoryId")
    })
    public FavoriteResponse createFavorite(User user, CreateFavoriteRequest request) {
        Food food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food", request.getFoodId().toString()));
        Favorite favorite = CreateFavoriteRequest.fromRequest(food);
        favorite.setUser(user);
        favorite = favoriteRepository.save(favorite);
        return FavoriteResponse.fromEntity(favorite);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "favorites", key = "#user.id"),
            @CacheEvict(value = "foods", key = "#request.categoryId")
    })
    public FavoriteResponse deleteFavorite(User user, CreateFavoriteRequest request) {
        Favorite favorite = favoriteRepository.findByUserIdAndFoodId(user.getId(), request.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Favorite", request.getFoodId().toString()));
        favoriteRepository.delete(favorite);
        return FavoriteResponse.fromEntity(favorite);
    }

    @Transactional
    @Cacheable(value = "favorites", key = "#user.id")
    public List<FavoriteResponse> listFoodFavorites(User user) {
        List<Favorite> favorites = favoriteRepository.findAllByUserId(user.getId());
        return FavoriteResponse.fromEntities(favorites);
    }
}
