package com.example.demo_cache_ignite.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {

    @Value("${cacheName:test-client}")
    private String cacheName;

    @Value("${igniteWorkDirectory:}")
    private String igniteWorkDirectory;

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(value = "cacheProvider", havingValue = "ignite")
    public Ignite ignite() {
        CacheConfiguration<IgniteKey, IgniteValue> cacheCfg = new CacheConfiguration<IgniteKey, IgniteValue>(cacheName).setSqlSchema("PUBLIC");
        cacheCfg.setIndexedTypes(IgniteKey.class, IgniteValue.class);

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(true);
        cfg.setCacheConfiguration(cacheCfg);
        if (igniteWorkDirectory != null && !igniteWorkDirectory.isEmpty()) {
            cfg.setWorkDirectory(igniteWorkDirectory);
        }
        return Ignition.start(cfg);
    }

}
