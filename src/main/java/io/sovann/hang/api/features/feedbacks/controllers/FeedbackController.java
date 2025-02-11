package io.sovann.hang.api.features.feedbacks.controllers;

import io.sovann.hang.api.annotations.*;
import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.commons.controllers.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.feedbacks.payloads.*;
import io.sovann.hang.api.features.feedbacks.services.*;
import io.sovann.hang.api.features.users.securities.*;
import java.util.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIURLs.FEEDBACK)
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackServiceImpl feedbackService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    public BaseResponse<FeedbackResponse> createFeedback(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateFeedbackRequest request
    ) {
        return callback.execute(() -> feedbackService.createFeedback(user != null ? user.getUser() : null, request),
                "Feedback failed to create",
                null);
    }

    @GetMapping("/list")
    public BaseResponse<List<FeedbackResponse>> listFeedbacksOfTable(
            @CurrentUser CustomUserDetails user,
            @RequestParam UUID tableId
    ) {
        return callback.execute(() -> feedbackService.listFeedbacksOfTable(user != null ? user.getUser() : null, tableId),
                "Feedbacks failed to list",
                null);
    }

    @GetMapping("/rate")
    public BaseResponse<RateResponse> getAverageRateOfFeedbacks(
            @CurrentUser CustomUserDetails user,
            @RequestParam(required = false) UUID tableId
    ) {
        return callback.execute(() -> feedbackService.getAverageRateOfFeedbacks(user != null ? user.getUser() : null, tableId),
                "Feedbacks failed to list",
                null);
    }
}
