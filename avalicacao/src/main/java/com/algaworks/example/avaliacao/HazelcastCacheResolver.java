package com.algaworks.example.avaliacao;

//import com.giffing.bucket4j.spring.boot.starter.config.cache.SyncCacheResolver;
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.map.IMap;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import io.github.bucket4j.grid.hazelcast.HazelcastProxyManager;
//import org.springframework.stereotype.Component;
//
//@Component(value = "rateLimitingBucketCacheResolver")
//public class HazelcastCacheResolver implements SyncCacheResolver {
//
//    private final HazelcastInstance hazelcastInstance;
//
//    public HazelcastCacheResolver(HazelcastInstance hazelcastInstance) {
//        this.hazelcastInstance = hazelcastInstance;
//    }
//
//    @Override
//    public ProxyManager<String> resolve(String cacheName) {
//        IMap<String, byte[]> map = hazelcastInstance.getMap(cacheName);
//        return new HazelcastProxyManager<>(map);
//    }
//
//}

//
//import com.giffing.bucket4j.spring.boot.starter.config.cache.SyncCacheResolver;
//import com.hazelcast.core.HazelcastInstance;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import io.github.bucket4j.grid.jcache.JCacheProxyManager;
//import org.springframework.stereotype.Component;
//
//@Component(value = "rateLimitingBucketCacheResolver")
//public class HazelcastCacheResolver implements SyncCacheResolver {
//
//    private final HazelcastInstance hazelcastInstance;
//
//    public HazelcastCacheResolver(HazelcastInstance hazelcastInstance) {
//        this.hazelcastInstance = hazelcastInstance;
//    }
//
//    @Override
//    public ProxyManager<String> resolve(String cacheName) {
//        return new JCacheProxyManager<>(hazelcastInstance.getCacheManager().getCache(cacheName));
//    }
//
//}

