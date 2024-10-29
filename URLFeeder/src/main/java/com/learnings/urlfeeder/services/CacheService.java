package com.learnings.urlfeeder.services;

import com.learnings.urlfeeder.codec.URLSerializationCodec;
import com.learnings.urlfeeder.entities.URL;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;

@Service
public class CacheService {

    private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);

    @Value("${com.learnings.urlfeeder.service.cache.ttl}")
    private Integer ttl;
    @Value("${com.learnings.urlfeeder.service.cache.url}")

    private String url;

    private RedisClient redisClient = null;

    private StatefulRedisConnection<String, URL> statefulRedisConnection = null;

    public URL get(String key) {
        URL url = statefulRedisConnection.sync().get(key);
        if (url != null) {
            LOG.info("Serving from cache, for key: {}!", key);
        } else {
            LOG.info("Cache miss, for the key: {}!", key);
        }
        return url;
    }

    public void set(URL url) {
        long ttlSeconds = TimeUnit.DAYS.toSeconds(this.ttl);
        statefulRedisConnection.sync().setex(url.getUrl(), ttlSeconds, url);
    }

    @PostConstruct
    private void init() {
        LOG.info("Post init called");
        redisClient = RedisClient.create(url);
        statefulRedisConnection = redisClient.connect(new URLSerializationCodec());
    }

    @PreDestroy
    private void destroy() {
        LOG.info("Pre destroy called");
        if (statefulRedisConnection != null) {
            statefulRedisConnection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }

}
