package io.sovann.hang.api.features.users.payloads.request;

import lombok.*;

@Getter
@Setter
@ToString
public class LoginFrontOfficeRequest {
    private String email;
    private String password;
}
