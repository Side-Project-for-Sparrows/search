package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.post.PostSearchRequest;
import com.sparrows.search.search.model.dto.post.PostSearchResponse;
import com.sparrows.search.search.model.entity.ElasticPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostSearchStrategy implements SearchStrategy<PostSearchRequest, PostSearchResponse> {
    private final ElasticsearchOperations elasticsearchOperations;
    private final KafkaProperties kafkaProperties;

    @Override
    public PostSearchResponse search(PostSearchRequest request) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q ->
                        q.bool(b ->
                                b.must(mb -> mb
                                        .multiMatch(mm -> mm
                                                .fields("title^2", "content")
                                                .query(request.getQuery())
                                        )
                                ).filter(fb -> fb
                                        .term(t -> t
                                                .field("boardId")
                                                .value(String.valueOf(request.getBoardId()))
                                        )
                                )
                        )
                )
                .build();
        /*
        NativeQuery query = NativeQuery.builder()
                .withQuery(q->
                        q.multiMatch(
                                m->
                                        m.fields("title^2", "content")
                                                .query(request.getQuery())
                        )
                )
                .build();

         */

        SearchHits<ElasticPost> searchHits =
                elasticsearchOperations.search(query, ElasticPost.class);

        return new PostSearchResponse(searchHits.stream()
                .map(hit ->  Long.parseLong(hit.getContent().getId()))
                .collect(Collectors.toList()));
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getPost();
    }
}