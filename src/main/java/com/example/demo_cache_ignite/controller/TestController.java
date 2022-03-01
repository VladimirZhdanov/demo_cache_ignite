package com.example.demo_cache_ignite.controller;

import com.example.demo_cache_ignite.CacheService;
import com.example.demo_cache_ignite.ignite.CacheServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    private final CacheService cacheService;

    @GetMapping("/status/check")
    public String status() {
        cacheService.putCache("23432242", Arrays.asList("Test 123", "Test 321"));

        Iterator<String> cache = cacheService.getCache("23432242");

        while (cache.hasNext()) {
            LOGGER.info(cache.next());
        }

        return "Working ...";
    }
}
