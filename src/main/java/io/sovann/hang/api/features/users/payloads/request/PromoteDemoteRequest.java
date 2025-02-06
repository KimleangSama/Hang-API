package io.sovann.hang.api.features.users.payloads.request;

import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class PromoteDemoteRequest {
    private UUID groupId;
    private UUID userId;
    private String username;
    private List<UUID> roles;
}
