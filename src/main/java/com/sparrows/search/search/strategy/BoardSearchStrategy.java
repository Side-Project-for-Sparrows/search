package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.board.BoardSearchRequest;
import com.sparrows.search.search.model.dto.board.BoardSearchResponse;
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

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getBoard();
    }
}