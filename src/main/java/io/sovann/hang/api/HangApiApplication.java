package io.sovann.hang.api;

import com.redis.om.spring.annotations.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cache.annotation.*;

@SpringBootApplication
@EnableCaching
@EnableRedisEnhancedRepositories(basePackages = {
        "io.sovann.hang.api.features"
})
public class HangApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HangApiApplication.class, args);
    }

}
