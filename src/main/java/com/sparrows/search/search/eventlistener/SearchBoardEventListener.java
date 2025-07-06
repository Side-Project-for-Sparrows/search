package com.sparrows.search.search.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.search.kafka.payload.board.BoardCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.port.in.SearchUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchBoardEventListener {
    private final ObjectMapper objectMapper;
    private final SearchUsecase searchUsecase;
    private final KafkaProperties kafkaProperties;
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000, multiplier = 2),
            dltTopicSuffix = ".dlt",
            autoCreateTopics = "true"
    )
    @KafkaListener(topics = "${kafka.topic.board.create}")
    public void handleBoardCreatedEvent(String message) throws JsonProcessingException {
        BoardCreatedPayload payload = objectMapper.readValue(message, BoardCreatedPayload.class);
        log.info("board created payload: {}", payload);

        searchUsecase.save(kafkaProperties.getAggregateType().getBoard(), payload);
    }

    @KafkaListener(topics = "${kafka.topic.board.create}.dlt")
    public void handleDlt(String message) {
//        log.error("DLT 메시지 수신: {}", message);
        // 저장, 알림, 재처리 로직 등
    }
}
