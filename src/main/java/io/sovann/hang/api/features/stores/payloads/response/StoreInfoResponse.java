package io.sovann.hang.api.features.stores.payloads.response;

import io.sovann.hang.api.features.stores.entities.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StoreInfoResponse {
    private List<OperatingHourResponse> operatingHours;
    private List<OrderingOptionResponse> orderOptions;
    private List<PaymentMethod> paymentMethods;
}
