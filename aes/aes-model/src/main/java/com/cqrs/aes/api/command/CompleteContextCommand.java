package com.cqrs.aes.api.command;

import com.cqrs.aes.model.CompleteData;
import com.cqrs.aes.model.ContextId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Value
@Builder
@EqualsAndHashCode
public class CompleteContextCommand {
    @NonNull
    @TargetAggregateIdentifier
    ContextId id;
    @NonNull
    CompleteData data;
    @NonNull
    Long chunkTotal;
}
