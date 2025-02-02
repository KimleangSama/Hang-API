package io.sovann.hang.api.features.stores.payloads.request.updates;

import io.sovann.hang.api.configs.MMConfig;
import io.sovann.hang.api.features.stores.entities.OrderingOption;
import io.sovann.hang.api.features.stores.entities.Store;
import io.sovann.hang.api.features.stores.payloads.request.CreateFeeRangeRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UpdateOrderingOptionRequest {
    private UUID id;
    private String name;
    private String description;
    private List<UpdateFeeRangeRequest> feeRanges;
}
