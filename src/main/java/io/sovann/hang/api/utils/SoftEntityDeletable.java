package io.sovann.hang.api.utils;

import io.sovann.hang.api.exceptions.*;
import io.sovann.hang.api.features.commons.entities.*;
import io.sovann.hang.api.features.users.entities.*;
import io.sovann.hang.api.features.users.enums.*;
import io.sovann.hang.api.features.users.securities.*;
import java.util.*;
import java.util.stream.*;
import lombok.extern.slf4j.*;

@Slf4j
public class SoftEntityDeletable {
    // Validate entity deletion state
    public static <T> void throwErrorIfSoftDeleted(T entity, Class<T> entityClass) {
        if (entity == null) {
            throw new ResourceNotFoundException(entityClass.getName(), "id: " + "unknown");
        }

        if (entity instanceof EntityDeletable deletableEntity) {
            // Check if already deleted
            if (deletableEntity.getDeletedAt() != null || deletableEntity.getDeletedBy() != null) {
                throw new ResourceDeletedException(entityClass, deletableEntity.getDeletedAt(), deletableEntity.getDeletedBy());
            }
        }
    }

    public static void throwErrorIfSoftDeleted(User user) {
        throwErrorIfSoftDeleted(user, User.class);
        // Ensure user status is active
        if (user.getStatus() == AuthStatus.blocked || user.getStatus() == AuthStatus.deleted) {
            throw new ResourceForbiddenException(user.getUsername(), User.class);
        }
    }

    // Validate roles and handle invalid roles in one place
    private static void checkUserRoles(Collection<Role> roles) {
        Set<String> validRoleNames = EnumSet.allOf(AuthRole.class).stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        roles.forEach(role -> {
            throwErrorIfSoftDeleted(role, Role.class);
            if (!validRoleNames.contains(role.getName().name())) {
                throw new ResourceNotFoundException("Role", "name");
            }
        });
    }
}
