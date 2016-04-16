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
    ContextData data;

    public ContextAggregate() {}

    @CommandHandler
    public ContextAggregate(CreateContextCommand command) {
        apply(ContextCreatedEvent.builder()
                .id(command.getId()).data(command.getData())
                .build());
    }

    @CommandHandler
    public void chunkProcessed(ProcessChunkContextCommand command) {
        apply(ContextChunkProcessedEvent.builder()
                .id(command.getId())
                .data(command.getData())
                .build());
    }

    @CommandHandler
    public void completed(CompleteContextCommand command) {
        apply(ContextCompletedEvent.builder()
                .id(command.getId())
                .build());
    }

    @EventHandler
    public void on(ContextCreatedEvent event) {
        this.id = event.getId();
        this.data = event.getData();
    }
}
