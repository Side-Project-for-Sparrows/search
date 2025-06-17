package com.sparrows.search.kafka.payload.log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogCreatedPayload {
    private String traceId;
    private String spanId;
    private String parentSpanId;
    private String method;
    private String level;
    private String message;
}
