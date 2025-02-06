package io.sovann.hang.api.features.users.payloads.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RegisterVerifyRequest {
    private String email;
    private String username;
    private String code;
}