package com.example.demo_cache_ignite.test;

public class Test {
    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();
        threadLocalValue.set(1);
        Integer result = threadLocalValue.get();
        System.out.println(result);
        threadLocalValue.remove();

        result = threadLocalValue.get();
        System.out.println(result);

    }
}
