package ru.perm.v.tutorials.config

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableCaching
class CacheConfig : CachingConfigurerSupport() {
    @Bean
    override fun cacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()
        val caches: MutableList<Cache> = ArrayList<Cache>()
        caches.add(ConcurrentMapCache("products"))
        caches.add(ConcurrentMapCache("group_products"))
        caches.add(ConcurrentMapCache("product_echo"))
        caches.add(ConcurrentMapCache("allGroupProductDTO"))
        cacheManager.setCaches(caches)
        return cacheManager
    }
}