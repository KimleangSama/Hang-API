package io.sovann.hang.api.features.users.repos;

import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

}
