package com.cqrs.aes.model;

import com.cqrs.aes.api.command.CompleteContextCommand;
import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.command.ProcessChunkContextCommand;
import com.cqrs.aes.api.event.ContextChunkProcessedEvent;
import com.cqrs.aes.api.event.ContextCompletedEvent;
import com.cqrs.aes.api.event.ContextCreatedEvent;
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
    public ContextAggregate(CreateContextCommand command) {
        apply(ContextCreatedEvent.builder()
                .id(command.getId()).data(command.getData())
                .build());
    }

    public void chunkProcessed(ProcessChunkContextCommand command) {
        if (isCompleted()) {
            throw new IllegalStateException();
        }
        apply(ContextChunkProcessedEvent.builder()
                .id(command.getId())
                .data(command.getData())
                .build());
    }

    public void completed(CompleteContextCommand command) {
        apply(ContextCompletedEvent.builder()
                .id(command.getId())
                .data(command.getData())
                .chunksTotal(command.getChunkTotal())
                .build());
    }

    public boolean isCompleted() {
        return chunksTotal != null && processedChunksCount == chunksTotal;
    }

    @EventHandler
    public void on(ContextCreatedEvent event) {
        this.id = event.getId();
        this.createData = event.getData();
        this.processedChunksCount = 0;
        this.chunksTotal = null;
    }

    @EventHandler
    public void on(ContextChunkProcessedEvent event) {
        this.processedChunksCount++;
    }

    @EventHandler
    public void on(ContextCompletedEvent event) {
        this.completeData = event.getData();
        this.chunksTotal = event.getChunksTotal();
    }
}
