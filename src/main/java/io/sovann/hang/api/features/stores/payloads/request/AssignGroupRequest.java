package io.sovann.hang.api.features.stores.payloads.request;

import io.sovann.hang.api.configs.MMConfig;
import io.sovann.hang.api.features.stores.entities.FeeRange;
import io.sovann.hang.api.features.stores.entities.OrderingOption;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class AssignGroupRequest {
    private UUID groupId;
    private List<UUID> storeIds;
}
