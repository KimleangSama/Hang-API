package io.sovann.hang.api.utils;

import java.util.*;
import org.springframework.util.*;

public class RandomString {
    public static String make(int length) {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "").substring(0, length);
    }
}
