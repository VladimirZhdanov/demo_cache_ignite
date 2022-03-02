package com.example.demo_cache_ignite.ignite;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
public class IgniteValue implements Serializable {

    @QuerySqlField(index = true)
    private final String specificRecord;

    @QuerySqlField(index = true)
    private final Timestamp created;
}
