package io.sovann.hang.api.features.commons.entities;

import java.time.*;
import java.util.*;

public interface EntityDeletable {
    UUID getDeletedBy();

    LocalDateTime getDeletedAt();
}
