package com.cqrs.aes.model;

import com.cqrs.aes.api.command.CompleteContextCommand;
import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.command.ProcessChunkContextCommand;
import com.cqrs.aes.api.event.*;
import com.cqrs.aes.handler.command.ContextCommandHandler;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ContextAggregateTest {
    public static final CompleteData COMPLETE_DATA = CompleteData.builder().data("Some Data").build();
    public static final ContextId ID = ContextId.createNew();
    public static final CreateData DATA = CreateData.builder().data("Some Data").build();
    public static final ChunkData CHUNK_DATA = ChunkData.builder().data("Some Data").build();

    private FixtureConfiguration fixture;

    @Before
    @SuppressWarnings("unchecked")
    public void before() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(ContextAggregate.class);
        fixture.registerAnnotatedCommandHandler(
                new ContextCommandHandler(fixture.getRepository()));
    }

    @Test
    public void testCreateContext() throws Exception {
        fixture.given()
                .when(CreateContextCommand.builder()
                        .id(ID).data(DATA)
                        .build())
                .expectEvents(ContextCreatedEvent.builder()
                        .id(ID).data(DATA)
                        .build());
    }

    @Test
    public void testProcessChunkContext() throws Exception {
        fixture.given(ContextCreatedEvent.builder()
                .id(ID).data(DATA)
                .build())
                .when(ProcessChunkContextCommand.builder()
                        .id(ID).data(CHUNK_DATA)
                        .build())
                .expectEvents(ContextChunkProcessedEvent.builder()
                        .id(ID).data(CHUNK_DATA)
                        .build());
    }

    @Test
    public void testContextChunkProcessedUnordered() throws Exception {
        fixture.given()
                .when(ProcessChunkContextCommand.builder()
                        .id(ID).data(CHUNK_DATA)
                        .build())
                .expectEvents(ContextChunkProcessedUnorderedEvent.builder()
                        .id(ID).data(CHUNK_DATA)
                        .build());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCompleteContext() throws Exception {
        fixture.given(Arrays.asList(
                ContextCreatedEvent.builder().id(ID).data(DATA).build(),
                ContextChunkProcessedEvent.builder().id(ID).data(CHUNK_DATA).build()))
                .when(CompleteContextCommand.builder()
                        .id(ID).data(COMPLETE_DATA).chunksTotal(1L)
                        .build())
                .expectEvents(ContextCompletedEvent.builder()
                        .id(ID).data(COMPLETE_DATA).chunksTotal(1L)
                        .build());
    }

    @Test
    public void testContextCompletedUnordered() throws Exception {
        fixture.given()
                .when(CompleteContextCommand.builder()
                        .id(ID).data(COMPLETE_DATA).chunksTotal(1L)
                        .build())
                .expectEvents(ContextCompletedUnorderedEvent.builder()
                        .id(ID).data(COMPLETE_DATA).chunksTotal(1L)
                        .build());
    }
}
