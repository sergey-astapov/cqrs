package com.cqrs.aes.model;

import com.cqrs.aes.api.command.CompleteContextCommand;
import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.command.ProcessChunkContextCommand;
import com.cqrs.aes.api.event.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContextAggregate extends AbstractAnnotatedAggregateRoot {
    @AggregateIdentifier
    ContextId id;
    CreateData createData;
    CompleteData completeData;
    long processedChunksCount = 0;
    Long chunksTotal;

    public ContextAggregate() {}

    @CommandHandler
    public ContextAggregate(CreateContextCommand c) {
        apply(ContextCreatedEvent.builder()
                .id(c.getId()).data(c.getData())
                .build());
    }

    @CommandHandler
    public ContextAggregate(ProcessChunkContextCommand c) {
        apply(ContextChunkProcessedUnorderedEvent.builder()
                .id(c.getId()).data(c.getData())
                .build());
    }

    @CommandHandler
    public ContextAggregate(CompleteContextCommand c) {
        apply(ContextCompletedUnorderedEvent.builder()
                .id(c.getId()).data(c.getData()).chunksTotal(c.getChunksTotal())
                .build());
    }

    public void processed(CreateContextCommand c) {
        apply(ContextCreatedUnorderedEvent.builder()
                .id(c.getId()).data(c.getData())
                .build());
    }

    public void processed(ProcessChunkContextCommand c) {
        if (isCompleted()) {
            throw new IllegalStateException();
        }
        apply(ContextChunkProcessedEvent.builder()
                .id(c.getId()).data(c.getData())
                .build());
    }

    public void processed(CompleteContextCommand c) {
        apply(ContextCompletedEvent.builder()
                .id(c.getId()).data(c.getData()).chunksTotal(c.getChunksTotal())
                .build());
    }

    public boolean isCompleted() {
        return chunksTotal != null && processedChunksCount == chunksTotal;
    }

    @EventHandler
    public void on(ContextCreatedEvent e) {
        this.id = e.getId();
        this.createData = e.getData();
        this.processedChunksCount = 0;
        this.chunksTotal = null;
    }

    @EventHandler
    public void on(ContextCreatedUnorderedEvent e) {
        this.createData = e.getData();
    }

    @EventHandler
    public void on(ContextChunkProcessedEvent e) {
        this.processedChunksCount++;
    }

    @EventHandler
    public void on(ContextChunkProcessedUnorderedEvent e) {
        this.id = e.getId();
        this.processedChunksCount = 1;
        this.chunksTotal = null;
    }

    @EventHandler
    public void on(ContextCompletedEvent e) {
        this.completeData = e.getData();
        this.chunksTotal = e.getChunksTotal();
    }

    @EventHandler
    public void on(ContextCompletedUnorderedEvent e) {
        this.id = e.getId();
        this.processedChunksCount = 0;
        this.completeData = e.getData();
        this.chunksTotal = e.getChunksTotal();
    }
}
