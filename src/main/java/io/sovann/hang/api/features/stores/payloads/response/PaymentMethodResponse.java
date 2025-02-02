package io.sovann.hang.api.features.stores.payloads.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class PaymentMethodResponse {
    private UUID id;
    private String method;
}
