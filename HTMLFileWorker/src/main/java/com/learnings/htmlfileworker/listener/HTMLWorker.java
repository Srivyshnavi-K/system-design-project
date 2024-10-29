package com.learnings.htmlfileworker.listener;

import com.learnings.htmlfileworker.model.URL;
import com.learnings.htmlfileworker.service.URLProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class HTMLWorker {

    private static final Logger LOG = LoggerFactory.getLogger(HTMLWorker.class);


    @Autowired
    private URLProcessor urlProcessor;

    @KafkaListener(topics = "html_topic", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload URL message, @Headers MessageHeaders headers) {
        LOG.info("Received message: {}, headers: {}", message, headers);
        urlProcessor.process(message.getUrl(), message.getId());
    }
}
