package com.cqrs.aes.handler.command;

import com.cqrs.aes.api.command.CompleteContextCommand;
import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.command.ProcessChunkContextCommand;
import com.cqrs.aes.model.ContextAggregate;
import com.cqrs.aes.model.ContextId;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;

@Slf4j
public class ContextCommandHandler {
    private Repository<ContextAggregate> repository;

    public ContextCommandHandler(Repository<ContextAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(CreateContextCommand command) {
        log.debug("Handle command: {}", command);
        ContextAggregate ctx = load(command.getId());
        log.debug("Ctx found, id: {}, ctx: {}", command.getId(), ctx);
        if (ctx == null) {
            repository.add(new ContextAggregate(command));
        } else {
            ctx.processed(command);
        }
    }

    @CommandHandler
    public void handle(ProcessChunkContextCommand command) {
        log.debug("Handle command: {}", command);
        ContextAggregate ctx = load(command.getId());
        log.debug("Ctx found, id: {}, ctx: {}", command.getId(), ctx);
        if (ctx == null) {
            repository.add(new ContextAggregate(command));
        } else {
            ctx.processed(command);
        }
    }

    @CommandHandler
    public void handle(CompleteContextCommand command) {
        log.debug("Handle command: {}", command);
        ContextAggregate ctx = load(command.getId());
        log.debug("Ctx found, id: {}, ctx: {}", command.getId(), ctx);
        if (ctx == null) {
            repository.add(new ContextAggregate(command));
        } else {
            ctx.processed(command);
        }
    }

    private ContextAggregate load(ContextId id) {
        try {
            return repository.load(id);
        } catch (AggregateNotFoundException e) {
            return null;
        }
    }
}
