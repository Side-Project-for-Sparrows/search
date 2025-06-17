package com.sparrows.search.search.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.search.kafka.payload.log.LogCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.port.in.SearchUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchLogEventListener {
    private final ObjectMapper objectMapper;
    private final SearchUsecase searchUsecase;
    private final KafkaProperties kafkaProperties;

    @KafkaListener(topics = "${kafka.topic.log.create}", groupId = "${kafka.groupId.search}"+5)
    public void handleFluentLog(String message) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(message);
        String innerLogJson = root.path("log").asText();
        LogCreatedPayload payload = objectMapper.readValue(innerLogJson, LogCreatedPayload.class);
        if(!"INFO".equals(payload.getLevel())) return;
        if(payload.getTraceId() == null) return;

        searchUsecase.save(kafkaProperties.getAggregateType().getLog(), payload);
    }

    @KafkaListener(topics = "fluent-raw-logs", groupId = "local-debug5")
    public void handleFluentLogNode(String obj) {
        try {
            JsonNode root = objectMapper.readTree(obj);

            // 내부 log 필드에 있는 JSON 문자열 파싱
            String logString = root.path("log").asText();
            JsonNode logNode = objectMapper.readTree(logString);

            String traceId = getSafe(logNode, "X_TRACE_ID");
            String spanId = getSafe(logNode, "X_SPAN_ID");
            String parentSpanId = getSafe(logNode, "X_PARENT_SPAN_ID");
            String method = getSafe(logNode, "METHOD");
            String level = getSafe(logNode, "level");
            String message = getSafe(logNode, "message");

            log.info("📝 [{}] [{}] [{} -> {}] [{}] {}", level, method, parentSpanId, spanId, traceId, message);

        } catch (Exception e) {
            log.warn("❌ Failed to parse FluentBit log: {}", obj, e);
        }
    }

    private String getSafe(JsonNode node, String key) {
        return node.has(key) ? node.get(key).asText() : "-";
    }
}
