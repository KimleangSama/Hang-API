package io.sovann.hang.api;

import org.springframework.boot.builder.*;
import org.springframework.boot.web.servlet.support.*;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HangApiApplication.class);
    }

}
