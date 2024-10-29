package com.learnings.htmlfileworker.controller;

import com.learnings.htmlfileworker.model.PageInfo;
import com.learnings.htmlfileworker.service.URLProcessor;
import com.learnings.htmlfileworker.service.URLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class HTMLURLController {

    private static final Logger LOG = LoggerFactory.getLogger(HTMLURLController.class);

    @Autowired
    private URLProcessor urlProcessor;

    @Autowired
    private URLService urlService;

    @GetMapping("/ping")
    public String ping() {
        return "Hello, from HTML worker service";
    }

    @GetMapping("/{urlId}")
    public ResponseEntity<PageInfo> get(@PathVariable String urlId) {
        Optional<PageInfo> opt = urlService.get(urlId);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping
    public ResponseEntity<Void> submitURL(@RequestBody String url) {
        LOG.info("URL received: {}", url);
        urlProcessor.process(url, UUID.randomUUID().toString());
        return ResponseEntity.ok().build();
    }
}