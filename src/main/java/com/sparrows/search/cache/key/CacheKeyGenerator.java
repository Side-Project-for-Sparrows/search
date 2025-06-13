package com.sparrows.search.cache.key;

@FunctionalInterface
public interface CacheKeyGenerator<K> {
    String generateKey(K key);
}