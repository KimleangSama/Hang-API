package io.sovann.hang.api.features.users.payloads.request;

import lombok.*;

@Getter
@Setter
@ToString
public class LoginBackOfficeRequest {
    private String username;
    private String password;
}
