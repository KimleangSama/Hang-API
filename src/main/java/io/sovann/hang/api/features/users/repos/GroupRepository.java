package io.sovann.hang.api.features.users.repos;

import io.sovann.hang.api.features.users.entities.Group;
import io.sovann.hang.api.features.users.entities.Role;
import io.sovann.hang.api.features.users.enums.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

}
