package com.cqrs.aes.api.command;

import com.cqrs.aes.model.ContextData;
import com.cqrs.aes.model.ContextId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Value
@Builder
@EqualsAndHashCode
public class CreateContextCommand {
    @TargetAggregateIdentifier
    ContextId id;
    ContextData data;
}
