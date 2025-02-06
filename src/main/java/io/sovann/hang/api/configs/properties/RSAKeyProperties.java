package io.sovann.hang.api.configs.properties;

import java.security.interfaces.*;
import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "rsa")
public record RSAKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
