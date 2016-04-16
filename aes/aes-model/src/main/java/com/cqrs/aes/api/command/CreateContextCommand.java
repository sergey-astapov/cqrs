package com.cqrs.aes.api.command;

import com.cqrs.aes.model.CreateData;
import com.cqrs.aes.model.ContextId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Value
@Builder
@EqualsAndHashCode
public class CreateContextCommand {
    @NonNull
    @TargetAggregateIdentifier
    ContextId id;
    @NonNull
    CreateData data;
}
