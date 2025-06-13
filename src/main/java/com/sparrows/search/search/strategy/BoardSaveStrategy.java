package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.payload.board.BoardCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.BoardSaveRequest;
import com.sparrows.search.search.model.dto.BoardSaveResponse;
import com.sparrows.search.search.model.dto.SaveRequest;
import com.sparrows.search.search.model.entity.ElasticBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

@Component
public class BoardSaveStrategy implements SaveStrategy<BoardSaveRequest, BoardSaveResponse> {
    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    KafkaProperties kafkaProperties;

    @Override
    public BoardSaveResponse save(BoardSaveRequest request) {
        elasticsearchOperations.save(ElasticBoard.from(request));

        return new BoardSaveResponse(true);
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getBoard();
    }

    public SaveRequest fromPayload(Object object) {
        BoardCreatedPayload payload = (BoardCreatedPayload) object;
        BoardSaveRequest request = new BoardSaveRequest();
        request.setDomain(kafkaProperties.getAggregateType().getPost());
        request.setId(((Integer)(payload.getBoardId())).longValue());
        request.setName(payload.getName());
        request.setDescription(payload.getDescription());
        return request;
    }
}