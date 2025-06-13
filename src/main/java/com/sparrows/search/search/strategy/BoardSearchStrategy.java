package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.payload.board.BoardCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.BoardSaveRequest;
import com.sparrows.search.search.model.dto.BoardSearchRequest;
import com.sparrows.search.search.model.dto.BoardSearchResponse;
import com.sparrows.search.search.model.dto.SaveRequest;
import com.sparrows.search.search.model.entity.ElasticBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BoardSearchStrategy implements SearchStrategy<BoardSearchRequest, BoardSearchResponse> {
    private final ElasticsearchOperations elasticsearchOperations;
    private final KafkaProperties kafkaProperties;

    @Override
    public BoardSearchResponse search(BoardSearchRequest request) {
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(q->
                        q.multiMatch(
                                m->
                                        m.fields("name^2", "description")
                                                .query(request.getQuery())
                        )
                )
                .build();

        SearchHits<ElasticBoard> searchHits =
                elasticsearchOperations.search(nativeQuery, ElasticBoard.class);

        return new BoardSearchResponse(searchHits.stream()
                .map(hit -> Long.parseLong(hit.getContent().getId()))
                .collect(Collectors.toList()));
    }

    public SaveRequest fromPayload(Object object) {
        BoardCreatedPayload payload = (BoardCreatedPayload) object;
        BoardSaveRequest request = new BoardSaveRequest();
        request.setDomain(kafkaProperties.getAggregateType().getBoard());
        request.setId(((Integer)payload.getBoardId()).longValue());
        request.setName(payload.getName());
        request.setDescription(payload.getDescription());
        return request;
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getBoard();
    }
}