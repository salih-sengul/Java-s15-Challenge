package com.workintech.util;

import java.util.Collections;
import java.util.Map;

public class IdGenerator {

    public static long generateId(Map<Long, ?> map) {
        if (map.isEmpty()) {
            return 1L;
        }
        return Collections.max(map.keySet()) + 1;
    }
}
