package com.sparrows.search.search.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.search.kafka.payload.log.K8sInfo;
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

    @KafkaListener(topics = "${kafka.topic.log.create}", groupId = "${kafka.groupId.search}")
    public void handleFluentLog(String message) throws JsonProcessingException {
        try{
            JsonNode root = objectMapper.readTree(message);

            String innerLogJson = root.path("log").asText();
            JsonNode k8sInfoNode = root.path("kubernetes");

            K8sInfo k8sInfo = objectMapper.treeToValue(k8sInfoNode, K8sInfo.class);
            LogCreatedPayload payload = objectMapper.readValue(innerLogJson, LogCreatedPayload.class);
            payload.setK8sInfo(k8sInfo);
            if(payload.getTraceId() == null) return;

            searchUsecase.save(kafkaProperties.getAggregateType().getLog(), payload);
        }catch (Exception e){
        }
    }

    private String getSafe(JsonNode node, String key) {
        return node.has(key) ? node.get(key).asText() : "-";
    }
}
