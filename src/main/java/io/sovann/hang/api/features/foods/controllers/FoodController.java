package io.sovann.hang.api.features.foods.controllers;

import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.foods.payloads.requests.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(APIURLs.FOOD)
@RequiredArgsConstructor
public class FoodController {
    private final FoodServiceImpl foodService;

    @PostMapping("/create")
    public BaseResponse<FoodResponse> createFood(@RequestBody CreateFoodRequest request) {
        log.info("Create food request: {}", request);
        return foodService.createFood(request);
    }
}
