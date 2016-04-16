package com.cqrs.aes.api.event;

import com.cqrs.aes.model.CreateData;
import com.cqrs.aes.model.ContextId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
public class ContextCreatedEvent {
    ContextId id;
    CreateData data;
}
