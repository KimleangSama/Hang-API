package io.sovann.hang.api.features.foods.services;

import io.sovann.hang.api.exceptions.ResourceForbiddenException;
import io.sovann.hang.api.exceptions.ResourceNotFoundException;
import io.sovann.hang.api.features.foods.entities.FoodCategory;
import io.sovann.hang.api.features.foods.payloads.requests.CreateFoodCategoryRequest;
import io.sovann.hang.api.features.foods.payloads.requests.FoodCategoryToggleRequest;
import io.sovann.hang.api.features.foods.payloads.responses.FoodCategoryResponse;
import io.sovann.hang.api.features.foods.repos.FoodCategoryRepository;
import io.sovann.hang.api.features.users.entities.Role;
import io.sovann.hang.api.features.users.entities.User;
import io.sovann.hang.api.features.users.enums.AuthRole;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl {
    private final FoodCategoryRepository foodCategoryRepository;

    public long count() {
        return foodCategoryRepository.count();
    }

    @Transactional
    @CacheEvict(value = "foodCategories", allEntries = true)
    public FoodCategoryResponse createFoodCategory(User user, CreateFoodCategoryRequest request) {
        FoodCategory foodCategory = CreateFoodCategoryRequest.fromRequest(request);
        foodCategory.setCreatedBy(user.getId());
        foodCategoryRepository.save(foodCategory);
        return FoodCategoryResponse.fromEntity(foodCategory);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "foodCategories", key = "#user.id")
    public List<FoodCategoryResponse> listFoodCategories(User user, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<FoodCategory> foodCategories = foodCategoryRepository.findAll(pageable).getContent();
        return FoodCategoryResponse.fromEntities(foodCategories);
    }

    public FoodCategoryResponse toggleFoodCategory(User user, FoodCategoryToggleRequest request, boolean toggleVisibility) {
        FoodCategory foodCategory = getCategoryById(user, request);
        if (toggleVisibility) {
            foodCategory.setHidden(!foodCategory.isHidden());
        } else {
            foodCategory.setAvailable(!foodCategory.isAvailable());
        }
        foodCategory.setUpdatedBy(user.getId());
        foodCategoryRepository.save(foodCategory);
        return FoodCategoryResponse.fromEntity(foodCategory);
    }

    @Transactional
    @CacheEvict(value = "foodCategories", key = "#user.id")
    public FoodCategoryResponse toggleFoodCategoryVisibility(User user, FoodCategoryToggleRequest request) {
        return toggleFoodCategory(user, request, true);
    }

    @Transactional
    @CacheEvict(value = "foodCategories", key = "#user.id")
    public FoodCategoryResponse toggleFoodCategoryAvailability(User user, FoodCategoryToggleRequest request) {
        return toggleFoodCategory(user, request, false);
    }

    @Transactional
    @CacheEvict(value = "foodCategories", key = "#user.id")
    public FoodCategoryResponse deleteFoodCategory(User user, FoodCategoryToggleRequest request) {
        FoodCategory foodCategory = getCategoryById(user, request);
        foodCategoryRepository.delete(foodCategory);
        return FoodCategoryResponse.fromEntity(foodCategory);
    }

    private FoodCategory getCategoryById(User user, FoodCategoryToggleRequest request) {
        FoodCategory foodCategory = foodCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Food category", request.getCategoryId().toString()));
        boolean isAdminOrManager = user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(role -> role.equals(AuthRole.admin) || role.equals(AuthRole.manager));
        if (!isAdminOrManager && !foodCategory.getCreatedBy().equals(user.getId())) {
            throw new ResourceForbiddenException(user.getUsername(), FoodCategory.class);
        }
        return foodCategory;
    }

}
