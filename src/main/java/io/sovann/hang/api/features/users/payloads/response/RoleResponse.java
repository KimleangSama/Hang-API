package io.sovann.hang.api.features.users.payloads.response;

import io.sovann.hang.api.features.users.entities.*;
import io.sovann.hang.api.features.users.enums.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;

@Getter
@Setter
@ToString
public class RoleResponse implements Serializable {
    private UUID id;
    private String name;

    private RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName().name();
    }

    public static Set<RoleResponse> fromRoles(Set<Role> roles) {
        return roles.stream().map(RoleResponse::new).collect(Collectors.toSet());
    }

    public static List<RoleResponse> fromEntities(List<Role> roles) {
        return roles.stream().map(RoleResponse::new).collect(Collectors.toList());
    }

    public static List<RoleResponse> fromEntitiesExclude(List<Role> roles, AuthRole authRole) {
        return roles.stream()
                .filter(role -> !role.getName().equals(authRole))
                .map(RoleResponse::new)
                .collect(Collectors.toList());
    }
}