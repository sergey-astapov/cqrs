package com.cqrs.aes.api.event;

import com.cqrs.aes.model.ContextId;
import com.cqrs.aes.model.CreateData;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
public class ContextCreatedUnorderedEvent {
    ContextId id;
    CreateData data;
}
