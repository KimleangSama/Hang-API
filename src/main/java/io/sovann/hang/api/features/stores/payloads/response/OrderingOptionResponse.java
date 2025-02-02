package io.sovann.hang.api.features.stores.payloads.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class OrderingOptionResponse {
    private UUID id;
    private String name;
    private String description;
    private List<FeeRangeResponse> feeRanges;
}
