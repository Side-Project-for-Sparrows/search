package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.payload.log.LogCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.SaveRequest;
import com.sparrows.search.search.model.dto.log.LogSaveRequest;
import com.sparrows.search.search.model.dto.log.LogSaveResponse;
import com.sparrows.search.search.model.entity.ElasticLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogSaveStrategy implements SaveStrategy<LogSaveRequest, LogSaveResponse> {

    private final ElasticsearchOperations elasticsearchOperations;
    private final KafkaProperties kafkaProperties;

    @Override
    public LogSaveResponse save(LogSaveRequest request) {
        elasticsearchOperations.save(ElasticLog.from(request));
        return new LogSaveResponse(true);
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getLog();
    }

    @Override
    public SaveRequest fromPayload(Object object) {
        try {
            LogCreatedPayload payload = (LogCreatedPayload) object;
            return LogSaveRequest.from(payload);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid log payload", e);
        }
    }
}
