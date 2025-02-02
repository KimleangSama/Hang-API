package io.sovann.hang.api.configs.securities;

public class UnRestrictedEndpoint {
    public static final String[] FREE_URLS = {
            "/api/v1/auth/**",
            "/error/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/aggregate/**",
            "/actuator/prometheus",
            "/actuator/health/**"
    };

    public static final String LOGOUT_URL = "/api/v1/auth/logout";
}

