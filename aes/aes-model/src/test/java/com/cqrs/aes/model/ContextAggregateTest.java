package com.cqrs.aes.model;

import com.cqrs.aes.api.command.CompleteContextCommand;
import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.command.ProcessChunkContextCommand;
import com.cqrs.aes.api.event.ContextChunkProcessedEvent;
import com.cqrs.aes.api.event.ContextCompletedEvent;
import com.cqrs.aes.api.event.ContextCreatedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class ContextAggregateTest {
    private FixtureConfiguration fixture;
    public static final ContextId ID = ContextId.createNew();
    public static final ContextData DATA = ContextData.builder().data("Some Data").build();
    public static final ChunkData CHUNK_DATA = ChunkData.builder().data("Some Data").build();

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(ContextAggregate.class);
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
    public void testCompleteContext() throws Exception {
        fixture.given(ContextCreatedEvent.builder()
                .id(ID).data(DATA)
                .build())
                .when(CompleteContextCommand.builder()
                        .id(ID)
                        .build())
                .expectEvents(ContextCompletedEvent.builder()
                        .id(ID)
                        .build());
    }
}
