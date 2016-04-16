package com.cqrs.aes.model;

import com.cqrs.aes.api.command.CreateContextCommand;
import com.cqrs.aes.api.event.ContextCreatedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class ContextAggregateTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(ContextAggregate.class);
    }

    @Test
    public void testCreateToDoItem() throws Exception {
        ContextId id = ContextId.createNew();
        ContextData data = ContextData.builder().data("Some Data").build();
        fixture.given()
                .when(CreateContextCommand.builder()
                        .id(id).data(data)
                        .build())
                .expectEvents(ContextCreatedEvent.builder()
                        .id(id).data(data)
                        .build());
    }
}
