package com.cqrs.aes.api.event;

import com.cqrs.aes.model.ChunkData;
import com.cqrs.aes.model.ContextId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
public class ContextChunkProcessedEvent {
    ContextId id;
    ChunkData data;
}
