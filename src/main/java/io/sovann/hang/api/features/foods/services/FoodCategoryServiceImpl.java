package io.sovann.hang.api.features.foods.services;

import io.sovann.hang.api.exceptions.ResourceForbiddenException;
import io.sovann.hang.api.exceptions.ResourceNotFoundException;
import io.sovann.hang.api.features.foods.entities.FoodCategory;
import io.sovann.hang.api.features.foods.payloads.CreateFoodCategoryRequest;
import io.sovann.hang.api.features.foods.payloads.FoodCategoryResponse;
import io.sovann.hang.api.features.foods.payloads.FoodCategoryToggleRequest;
import io.sovann.hang.api.features.foods.repos.FoodCategoryRepository;
import io.sovann.hang.api.features.stores.entities.Store;
import io.sovann.hang.api.features.stores.repos.StoreRepository;
import io.sovann.hang.api.features.users.entities.User;
import io.sovann.hang.api.utils.GroupMemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl {
    private final FoodCategoryRepository foodCategoryRepository;
    private final StoreRepository storeRepository;

    private Store validateUserAccess(User user, UUID storeId) {
        return storeRepository.findById(storeId)
                .filter(store -> store.getGroup() != null && GroupMemberUtils.isUserIsMemberOfGroup(user, store.getGroup().getMembers()))
                .orElseThrow(() -> new ResourceForbiddenException(user.getUsername(), FoodCategory.class));
    }

    @Transactional
    @CacheEvict(value = "foodCategories", key = "#request.storeId")
    public FoodCategoryResponse createFoodCategory(User user, CreateFoodCategoryRequest request) {
        Store store = validateUserAccess(user, request.getStoreId());
        FoodCategory foodCategory = CreateFoodCategoryRequest.fromRequest(request);
        foodCategory.setCreatedBy(user.getId());
        foodCategory.setStore(store);
        foodCategoryRepository.save(foodCategory);
        return FoodCategoryResponse.fromEntity(foodCategory);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "foodCategories", key = "#storeId")
    public List<FoodCategoryResponse> listFoodCategory(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", storeId.toString()));
        return FoodCategoryResponse.fromEntities(foodCategoryRepository.findAllByStore(store));
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
    @CacheEvict(value = "foodCategories", key = "#request.storeId")
    public FoodCategoryResponse toggleFoodCategoryVisibility(User user, FoodCategoryToggleRequest request) {
        return toggleFoodCategory(user, request, true);
    }

    @Transactional
    @CacheEvict(value = "foodCategories", key = "#request.storeId")
    public FoodCategoryResponse toggleFoodCategoryAvailability(User user, FoodCategoryToggleRequest request) {
        return toggleFoodCategory(user, request, false);
    }

    @Transactional
    @CacheEvict(value = "foodCategories", key = "#request.storeId")
    public FoodCategoryResponse deleteFoodCategory(User user, FoodCategoryToggleRequest request) {
        FoodCategory foodCategory = getCategoryById(user, request);
        foodCategoryRepository.delete(foodCategory);
        return FoodCategoryResponse.fromEntity(foodCategory);
    }

    private FoodCategory getCategoryById(User user, FoodCategoryToggleRequest request) {
        FoodCategory foodCategory = foodCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Food category", request.getCategoryId().toString()));
        if (!request.getStoreId().equals(foodCategory.getStore().getId())) {
            throw new ResourceForbiddenException(user.getUsername(), FoodCategory.class);
        }
        validateUserAccess(user, request.getStoreId());
        return foodCategory;
    }
}
