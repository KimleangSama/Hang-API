package io.sovann.hang.api.features.foods.services;

import io.sovann.hang.api.features.foods.entities.*;
import io.sovann.hang.api.features.foods.payloads.requests.*;
import io.sovann.hang.api.features.foods.payloads.responses.*;
import io.sovann.hang.api.features.foods.repos.*;
import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl {
    private final FoodRepository foodRepository;
    private final FoodFavoriteServiceImpl favoriteService;
    private final FoodCategoryRepository foodCategoryRepository;

    @Transactional
    @CacheEvict(value = "foods", key = "#request.categoryId", allEntries = true)
    public FoodResponse createFood(User user, CreateFoodRequest request) {
        FoodCategory foodCategory = foodCategoryRepository.findById(request.getCategoryId())
                .orElse(null);
        Food food = CreateFoodRequest.fromRequest(request);
        food.setCreatedBy(user.getId());
        food.setCategory(foodCategory);
        foodRepository.save(food);
        return FoodResponse.fromEntity(food);
    }

    @Transactional
    @Cacheable(value = "foods", key = "#categoryId")
    public List<FoodResponse> listFoodByCategoryId(
            User user,
            UUID categoryId
    ) {
        FoodCategory foodCategory = foodCategoryRepository.findById(categoryId)
                .orElse(null);
        if (foodCategory == null) {
            return Collections.emptyList();
        }
        List<Food> foods = foodRepository.findAllByCategory(foodCategory);
        if (user == null) {
            return FoodResponse.fromEntities(foods, Collections.emptyList());
        }
        List<FavoriteResponse> favorites = favoriteService.listFoodFavorites(user);
        return FoodResponse.fromEntities(foods, favorites);
    }

    // For admin only
    @Transactional
    @Cacheable(value = "foods")
    public List<FoodResponse> listFoods(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foods = foodRepository.findAll(pageable);
        return FoodResponse.fromEntities(foods.getContent(), Collections.emptyList());
    }

    @Transactional
    public long count() {
        return foodRepository.count();
    }

    @Transactional
    @CacheEvict(value = "foods", key = "#request.categoryId")
    public FoodResponse toggleFoodVisibility(User user, FoodToggleRequest request) {
        Food food = foodRepository.findById(request.getFoodId())
                .orElse(null);
        if (food == null) {
            return null;
        }
        food.setIsHidden(!food.getIsHidden());
        foodRepository.save(food);
        return FoodResponse.fromEntity(food);
    }

    @Transactional
    @CacheEvict(value = "foods", key = "#request.categoryId")
    public FoodResponse toggleFoodAvailability(User user, FoodToggleRequest request) {
        Food food = foodRepository.findById(request.getFoodId())
                .orElse(null);
        if (food == null) {
            return null;
        }
        food.setIsAvailable(!food.getIsAvailable());
        foodRepository.save(food);
        return FoodResponse.fromEntity(food);
    }

    @Transactional
    @CacheEvict(value = "foods", key = "#request.categoryId")
    public FoodResponse deleteFood(User user, FoodToggleRequest request) {
        Food food = foodRepository.findById(request.getFoodId())
                .orElse(null);
        if (food == null) {
            return null;
        }
        foodRepository.delete(food);
        return FoodResponse.fromEntity(food);
    }

    @Transactional
    @CacheEvict(value = "foods", key = "#foodId")
    public Optional<Food> getFoodById(UUID foodId) {
        return foodRepository.findById(foodId);
    }

    @Transactional
    @Cacheable(value = "foods")
    public List<FoodResponse> listFoodsWithCategory(User user) {
        List<Food> foods = foodRepository.findAll();
        if (user == null) {
            return FoodResponse.fromEntities(foods, Collections.emptyList());
        }
        List<FavoriteResponse> favorites = favoriteService.listFoodFavorites(user);
        return FoodResponse.fromEntities(foods, favorites);
    }
}
