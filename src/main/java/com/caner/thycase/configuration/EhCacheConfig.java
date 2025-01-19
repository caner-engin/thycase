package com.caner.thycase.configuration;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class EhCacheConfig {

    @Value("${transportationCacheTTL}")
    private int transportationCacheTTL;

    @Bean("jCacheCacheManager")
    public JCacheCacheManager jCacheCacheManager() {
        return new JCacheCacheManager(ehCacheManager());
    }

    @Bean
    public CacheManager ehCacheManager() {
        CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        CacheManager cacheManager = cachingProvider.getCacheManager();
        cacheManager.createCache("transportationCache", createCacheConfig(String.class,
                Object.class,
                new Duration(TimeUnit.MINUTES, transportationCacheTTL), 100
        ));

        return cacheManager;
    }

    private <K, V> javax.cache.configuration.Configuration<K, V> createCacheConfig(
            Class<K> keyType, Class<V> valueType, Duration ttl, int heap) {

        CacheConfiguration<K, V> ehCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(keyType,
                        valueType,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(heap, EntryUnit.ENTRIES)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(java.time.Duration.ofMinutes(ttl.getDurationAmount())))
                .build();

        return Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfig);
    }

    @Bean
    @Qualifier("ehCacheResolver")
    public CacheResolver ehCacheResolver() {
        return new SimpleCacheResolver(jCacheCacheManager());
    }

}
