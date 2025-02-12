package io.sovann.hang.api.configs.properties;

import java.util.*;
import lombok.*;
import org.springframework.boot.context.properties.*;

@Setter
@Getter
@ConfigurationProperties(prefix = "cors")
public class CORSProperties {
    private List<String> allowedOrigins;
}
