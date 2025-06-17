package com.sparrows.search.kafka.payload.log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class LogCreatedPayload {

    @JsonProperty("X_TRACE_ID")
    private String traceId;

    @JsonProperty("X_SPAN_ID")
    private String spanId;

    @JsonProperty("X_PARENT_SPAN_ID")
    private String parentSpanId;

    @JsonProperty("CLASS")
    private String clazz;

    @JsonProperty("METHOD")
    private String method;

    @JsonProperty("level")
    private String level;

    @JsonProperty("message")
    private String message;
}