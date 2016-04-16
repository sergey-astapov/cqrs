package com.cqrs.aes.api.command;

import com.cqrs.aes.model.ContextId;
import com.cqrs.aes.model.ChunkData;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Value
@Builder
@EqualsAndHashCode
public class ProcessChunkContextCommand {
    @NonNull
    @TargetAggregateIdentifier
    ContextId id;
    @NonNull
    ChunkData data;
}
