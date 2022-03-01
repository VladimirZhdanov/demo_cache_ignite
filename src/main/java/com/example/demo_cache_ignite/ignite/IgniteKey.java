package com.example.demo_cache_ignite.ignite;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class IgniteKey implements Serializable {

    @QuerySqlField(index = true)
    private final String requestId;

    @QuerySqlField(index = true)
    private final int rowNumber;
}


