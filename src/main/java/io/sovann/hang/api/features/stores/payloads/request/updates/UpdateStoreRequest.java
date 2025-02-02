package io.sovann.hang.api.features.stores.payloads.request.updates;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UpdateStoreRequest {
    private String name;
    private String logoUrl;
    private String color;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String googleMapAddress;

    private UUID groupId;

    private List<UpdateOperatingHourRequest> operatingHours;
    private List<UpdateOrderingOptionRequest> orderOptions;
    private List<UpdatePaymentMethodRequest> paymentMethods;
}
