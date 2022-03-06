package com.example.demo_cache_ignite.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@Configuration
@EnableScheduling
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

        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

        // Set initial IP addresses.
        // Note that you can optionally specify a port or a port range.
        ipFinder.setAddresses(Arrays.asList("127.0.0.1", "127.0.0.1:47500..47509"));

        spi.setIpFinder(ipFinder);

        IgniteConfiguration cfg = new IgniteConfiguration();

        // Override default discovery SPI.
        cfg.setDiscoverySpi(spi);

        // Set cache
        cfg.setCacheConfiguration(cacheCfg);

        if (igniteWorkDirectory != null && !igniteWorkDirectory.isEmpty()) {
            cfg.setWorkDirectory(igniteWorkDirectory);
        }

        // Start a node.
        return Ignition.start(cfg);
    }

}
