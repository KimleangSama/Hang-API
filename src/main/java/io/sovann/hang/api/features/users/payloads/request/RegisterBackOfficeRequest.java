package io.sovann.hang.api.features.users.payloads.request;

import io.sovann.hang.api.features.users.enums.*;
import jakarta.validation.constraints.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class RegisterBackOfficeRequest {
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private List<AuthRole> roles;
}