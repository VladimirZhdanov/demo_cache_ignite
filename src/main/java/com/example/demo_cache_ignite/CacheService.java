package com.example.demo_cache_ignite;

import java.util.Iterator;
import java.util.List;

public interface CacheService {
    void putCache(String key, List<String> value);
    Iterator<String> getCache(String key);
}
