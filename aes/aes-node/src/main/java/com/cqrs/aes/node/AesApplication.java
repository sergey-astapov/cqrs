package com.cqrs.aes.node;

import com.cqrs.aes.api.command.CompleteContextCommand;
import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.command.ProcessChunkContextCommand;
import com.cqrs.aes.model.ChunkData;
import com.cqrs.aes.model.CompleteData;
import com.cqrs.aes.model.ContextId;
import com.cqrs.aes.model.CreateData;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

@Slf4j
@SpringBootApplication
public class AesApplication implements CommandLineRunner {
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(AesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final String id = UUID.randomUUID().toString();
        CommandGateway gateway = context.getBean(CommandGateway.class);
        gateway.send(CreateContextCommand.builder()
                .id(ContextId.builder().id(id).build())
                .data(CreateData.builder().data("Some Data").build())
                .build());
        gateway.send(ProcessChunkContextCommand.builder()
                .id(ContextId.builder().id(id).build())
                .data(ChunkData.builder().data("Some Data").build())
                .build());
        gateway.send(CompleteContextCommand.builder()
                .id(ContextId.builder().id(id).build())
                .data(CompleteData.builder().data("Some Data").build())
                .chunksTotal(1L)
                .build());

        Thread.currentThread().join();
    }
}
