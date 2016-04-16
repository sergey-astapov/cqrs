package com.cqrs.aes.node;

import com.cqrs.aes.business.handler.command.ContextCommandHandler;
import com.cqrs.aes.business.handler.event.ContextEventHandler;
import com.cqrs.aes.model.ContextAggregate;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@AnnotationDriven
public class AesConfig {
    @Bean
    public SimpleCommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public DefaultCommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }

    @Bean
    public EventStore eventStore() {
        return new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));
    }

    @SuppressWarnings("unchecked")
    @Bean
    public EventSourcingRepository eventSourcingRepository(EventBus eventBus, EventStore eventStore) {
        EventSourcingRepository eventSourcingRepository = new EventSourcingRepository(ContextAggregate.class, eventStore);
        eventSourcingRepository.setEventBus(eventBus);
        return eventSourcingRepository;
    }

    @Bean
    public ContextCommandHandler contextCommandHandler(Repository<ContextAggregate> repository) {
        return new ContextCommandHandler(repository);
    }

    @Bean
    public ContextEventHandler contextEventHandler() {
        return new ContextEventHandler();
    }
}
