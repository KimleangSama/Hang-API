package io.sovann.hang.api.features.foods.services;

import io.sovann.hang.api.features.foods.entities.Food;
import io.sovann.hang.api.features.foods.entities.FoodCategory;
import io.sovann.hang.api.features.foods.payloads.requests.CreateFoodRequest;
import io.sovann.hang.api.features.foods.payloads.requests.FoodToggleRequest;
import io.sovann.hang.api.features.foods.payloads.responses.FoodCategoryResponse;
import io.sovann.hang.api.features.foods.payloads.responses.FoodResponse;
import io.sovann.hang.api.features.foods.repos.FoodCategoryRepository;
import io.sovann.hang.api.features.foods.repos.FoodRepository;
import io.sovann.hang.api.features.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl {
    private final FoodRepository foodRepository;
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
        return FoodResponse.fromEntities(foods);
    }

    // For admin only
    @Transactional
    @Cacheable(value = "foods")
    public List<FoodResponse> listFoods(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foods = foodRepository.findAll(pageable);
        return FoodResponse.fromEntities(foods.getContent());
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
}
