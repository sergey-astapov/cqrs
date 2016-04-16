package com.cqrs.aes.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ContextData {
    @NonNull
    String data;
}
