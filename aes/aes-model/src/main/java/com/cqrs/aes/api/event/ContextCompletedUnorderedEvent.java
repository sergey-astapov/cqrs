package com.cqrs.aes.api.event;

import com.cqrs.aes.model.CompleteData;
import com.cqrs.aes.model.ContextId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
public class ContextCompletedUnorderedEvent {
    @NonNull
    ContextId id;
    @NonNull
    CompleteData data;
    @NonNull
    Long chunksTotal;
}
