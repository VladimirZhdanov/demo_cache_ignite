package com.example.demo_cache_ignite.ignite;

import com.example.demo_cache_ignite.CacheService;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@ConditionalOnProperty(value = "cacheProvider", havingValue = "ignite")
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Value("${cacheName:test-client}")
    private String cacheName;

    private final Ignite ignite;

    @Override
    public void putCache(String key, List<String> value) {
        try (IgniteDataStreamer<IgniteKey, IgniteValue> streamer = ignite.dataStreamer(cacheName)) {
            for (int i = 0; i < value.size(); i++) {
                streamer.addData(new IgniteKey(key, i), new IgniteValue(value.get(i)));
            }
        }
        LOGGER.info(value.size() + " records added to cache");
    }

    @Override
    public Iterator<String> getCache(String key) {
        LOGGER.info("Hitting get cache");
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache(cacheName);
        String query = String.format("SELECT specificRecord FROM IgniteValue WHERE requestId = '%s'", key);
        LOGGER.info("Cache Query: " + query);
        Query<List<?>> sqlFieldsQuery = new SqlFieldsQuery(query);
        Iterator<List<?>> result = cache.query(sqlFieldsQuery).iterator();
        LOGGER.info("Cache not empty: " + result.hasNext());
        return result.hasNext() ? new IgniteIterator(result) : null;
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println("Cleaning cache...");
        LOGGER.info("Cleaning cache...");
    }

    private static class IgniteIterator implements Iterator<String> {

        private final Iterator<List<?>> cursorIterator;

        public IgniteIterator(Iterator<List<?>> cursorIterator) {
            this.cursorIterator = cursorIterator;
        }

        @Override
        public boolean hasNext() {
            return cursorIterator.hasNext();
        }

        @Override
        public String next() {
            List<?> row = cursorIterator.next();
            for (Object column : row) {
                if (column instanceof String) {
                    return column.toString();
                }
            }
            return null;
        }
    }
}
