package io.sovann.hang.api.configs.properties;

import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "loki")
public record LokiProperties(String url) {
}
