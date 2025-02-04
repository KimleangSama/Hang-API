package io.sovann.hang.api.features.foods.services;

import io.sovann.hang.api.exceptions.ResourceNotFoundException;
import io.sovann.hang.api.features.foods.entities.Favorite;
import io.sovann.hang.api.features.foods.entities.Food;
import io.sovann.hang.api.features.foods.payloads.requests.CreateFavoriteRequest;
import io.sovann.hang.api.features.foods.payloads.responses.FavoriteResponse;
import io.sovann.hang.api.features.foods.repos.FoodFavoriteRepository;
import io.sovann.hang.api.features.foods.repos.FoodRepository;
import io.sovann.hang.api.features.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodFavoriteServiceImpl {
    private final FoodRepository foodRepository;
    private final FoodFavoriteRepository favoriteRepository;

    @Transactional
    @CacheEvict(value = "favorites", key = "#user.id")
    public FavoriteResponse createFavorite(User user, CreateFavoriteRequest request) {
        Food food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food", request.getFoodId().toString()));
        Favorite favorite = CreateFavoriteRequest.fromRequest(food);
        favorite.setUser(user);
        favorite = favoriteRepository.save(favorite);
        return FavoriteResponse.fromEntity(favorite);
    }

    @Transactional
    @CacheEvict(value = "favorites", key = "#user.id")
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
