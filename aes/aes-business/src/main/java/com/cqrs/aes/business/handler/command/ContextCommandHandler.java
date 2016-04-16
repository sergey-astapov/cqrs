package com.cqrs.aes.business.handler.command;

import com.cqrs.aes.api.command.CompleteContextCommand;
import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.command.ProcessChunkContextCommand;
import com.cqrs.aes.model.ContextAggregate;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.annotation.CommandHandler;
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
        repository.add(new ContextAggregate(command));
    }

    @CommandHandler
    public void handle(ProcessChunkContextCommand command) {
        log.debug("Handle command: {}", command);
        ContextAggregate ctx = repository.load(command.getId());
        log.debug("Ctx found, id: {}, ctx: {}", command.getId(), ctx);
        ctx.chunkProcessed(command);
    }

    @CommandHandler
    public void handle(CompleteContextCommand command) {
        log.debug("Handle command: {}", command);
        ContextAggregate ctx = repository.load(command.getId());
        log.debug("Ctx found, id: {}, ctx: {}", command.getId(), ctx);
        ctx.completed(command);
    }
}
