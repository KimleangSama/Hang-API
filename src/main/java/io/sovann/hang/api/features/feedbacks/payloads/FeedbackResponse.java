package io.sovann.hang.api.features.feedbacks.payloads;

import io.sovann.hang.api.features.feedbacks.entities.*;
import io.sovann.hang.api.features.feedbacks.enums.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class FeedbackResponse {
    private UUID id;
    private UUID tableId;
    private String fullname;
    private String phone;
    private String comment;
    private Rating rating;

    public static FeedbackResponse fromEntity(Feedback feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setId(feedback.getId());
        response.setTableId(feedback.getTable().getId());
        response.setFullname(feedback.getFullname());
        response.setPhone(feedback.getPhone());
        response.setComment(feedback.getComment());
        response.setRating(feedback.getRating());
        return response;
    }
}
