package io.sovann.hang.api.features.users.payloads.request;

import io.sovann.hang.api.configs.*;
import io.sovann.hang.api.features.users.entities.*;
import lombok.*;
import org.modelmapper.*;

@Getter
@Setter
@ToString
public class CreateGroupRequest {
    private String name;
    private String description;

    public static Group fromRequest(CreateGroupRequest request) {
        ModelMapper mm = MMConfig.mapper();
        return mm.map(request, Group.class);
    }
}
