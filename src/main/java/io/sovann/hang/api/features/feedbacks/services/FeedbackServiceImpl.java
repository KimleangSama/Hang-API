package io.sovann.hang.api.features.feedbacks.services;

import io.sovann.hang.api.features.feedbacks.entities.*;
import io.sovann.hang.api.features.feedbacks.payloads.*;
import io.sovann.hang.api.features.feedbacks.repos.*;
import io.sovann.hang.api.features.tables.entities.*;
import io.sovann.hang.api.features.tables.services.*;
import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl {
    private final TableServiceImpl tableService;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public FeedbackResponse createFeedback(User user, CreateFeedbackRequest request) {
        Table table = tableService.findTableEntityById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));
        Feedback feedback = CreateFeedbackRequest.fromRequest(request);
        feedback.setTable(table);
        if (user != null) {
            feedback.setCreatedBy(user.getId());
        }
        feedbackRepository.save(feedback);
        return FeedbackResponse.fromEntity(feedback);
    }

    @Transactional
    public List<FeedbackResponse> listFeedbacksOfTable(User user, UUID tableId) {
        Table table = tableService.findTableEntityById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        List<Feedback> feedbacks = feedbackRepository.findAllByTable(table);
        return feedbacks.stream().map(FeedbackResponse::fromEntity).toList();
    }

    public Double getAverageRateOfFeedbacks(User user, UUID tableId) {
        Table table = tableService.findTableEntityById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        List<Feedback> feedbacks = feedbackRepository.findAllByTable(table);
        return feedbacks.stream().mapToDouble(r -> r.getRating().getValue()).sum() / feedbacks.size();
    }
}
