package io.sovann.hang.api.features.feedbacks.repos;

import io.sovann.hang.api.features.feedbacks.entities.*;
import io.sovann.hang.api.features.tables.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findAllByTable(Table table);
}
