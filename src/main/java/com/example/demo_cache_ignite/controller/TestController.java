package com.example.demo_cache_ignite.controller;

import com.example.demo_cache_ignite.CacheService;
import com.example.demo_cache_ignite.ignite.CacheServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    private final CacheService cacheService;

    @GetMapping("/put/{id}")
    public String put(@PathVariable String id) {
        cacheService.putCache(id, Arrays.asList("Test " + id, "Test " + id));
        return "Done";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable String id) {
        Iterator<String> cache = cacheService.getCache(id);

        while (cache.hasNext()) {
            LOGGER.info(cache.next());
        }

        return "Done";
    }

    @GetMapping("/get")
    public String get() {
        Iterator<String> cache = cacheService.getAllCache();

        while (cache != null && cache.hasNext()) {
            LOGGER.info(cache.next());
        }
        return "Done";
    }

    @GetMapping("/clean")
    @ResponseStatus(value = HttpStatus.OK)
    public void clean() {
        cacheService.clean();
    }
}
