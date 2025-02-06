package io.sovann.hang.api.features.users.repos;

import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {
    Page<GroupMember> findByGroupId(UUID id, Pageable pageable);

    long countByGroupId(UUID id);

    Optional<GroupMember> findByGroupIdAndUserId(UUID id, UUID userId);
}
