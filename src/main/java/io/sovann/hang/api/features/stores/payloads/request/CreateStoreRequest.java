package io.sovann.hang.api.features.stores.payloads.request;

import io.sovann.hang.api.configs.MMConfig;
import io.sovann.hang.api.features.stores.entities.Store;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CreateStoreRequest {
    private String name;
    private String logoUrl;
    private String color;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String googleMapAddress;

    private UUID groupId;

    private List<CreateOperatingHourRequest> operatingHours;
    private List<CreateOrderingOptionRequest> orderOptions;
    private List<CreatePaymentMethodRequest> paymentMethods;

    public static Store fromRequest(CreateStoreRequest request) {
        ModelMapper mapper = MMConfig.mapper();
        return mapper.map(request, Store.class);
    }
}
