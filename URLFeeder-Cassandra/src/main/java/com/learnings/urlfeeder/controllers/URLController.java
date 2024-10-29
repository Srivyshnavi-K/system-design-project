package com.learnings.urlfeeder.controllers;

import com.learnings.urlfeeder.constants.Constants;
import com.learnings.urlfeeder.entities.URL;
import com.learnings.urlfeeder.services.KafkaService;
import com.learnings.urlfeeder.services.URLService;
import org.apache.kafka.common.requests.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
public class URLController {
    private static final Logger LOG = LoggerFactory.getLogger(URLController.class);

    @Autowired
    URLService urlService;

    @GetMapping("/ping")
    public String pingURLService() {
        return "Hello!! from the URLFeederService.";
    }

    @GetMapping("/{id}")
    public ResponseEntity<URL> get(@PathVariable String id) {
        Optional<URL> opt = urlService.get(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> submitBatchURL(@RequestBody Set<URL> urls) {
        long startTime = System.currentTimeMillis();
        LOG.info("Batch request received: {}", urls);
        urls.forEach(u -> {
            u.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
            u.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            u.setTimesProcessed(0);
        });
        urlService.save(urls);
        LOG.info("Request processed in {} mills", (System.currentTimeMillis() - startTime));
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<URL> postURL(@RequestBody URL url){
        long startTime = System.currentTimeMillis();
        url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
        url.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        url.setTimesProcessed(0);
        LOG.info("URL received: {}", url);
        urlService.save(new HashSet<>(){{
            add(url);
        }});
        LOG.info("Request processed in {} mills", (System.currentTimeMillis() - startTime));
        return ResponseEntity.ok(url);
    }


}
