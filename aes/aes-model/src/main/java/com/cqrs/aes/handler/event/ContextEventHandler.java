package com.cqrs.aes.handler.event;

import com.cqrs.aes.api.event.ContextChunkProcessedEvent;
import com.cqrs.aes.api.event.ContextCompletedEvent;
import com.cqrs.aes.api.event.ContextCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.annotation.EventHandler;

@Slf4j
public class ContextEventHandler {
    @EventHandler
    public void on(ContextCreatedEvent event) {
        log.debug("On event: {}", event);
    }

    @EventHandler
    public void on(ContextChunkProcessedEvent event) {
        log.debug("On event: {}", event);
    }

    @EventHandler
    public void on(ContextCompletedEvent event) {
        log.debug("On event: {}", event);
    }
}
