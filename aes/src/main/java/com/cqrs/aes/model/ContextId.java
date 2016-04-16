package com.cqrs.aes.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ContextId {
    @NonNull
    String id;

    @Builder
    public static ContextId createNew() {
        return new ContextId(UUID.randomUUID().toString());
    }
}
