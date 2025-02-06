package io.sovann.hang.api.configs.properties;

import lombok.*;
import org.springframework.boot.context.properties.*;

@Setter
@Getter
@ConfigurationProperties(prefix = "cors")
public class CORSProperties {
    private String[] allowedOrigins;
}
