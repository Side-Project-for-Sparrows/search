package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.payload.board.PostCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.post.PostSaveRequest;
import com.sparrows.search.search.model.dto.post.PostSaveResponse;
import com.sparrows.search.search.model.dto.SaveRequest;
import com.sparrows.search.search.model.entity.ElasticPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSaveStrategy implements SaveStrategy<PostSaveRequest, PostSaveResponse> {
    private final ElasticsearchOperations elasticsearchOperations;
    private final KafkaProperties kafkaProperties;

    @Override
    public PostSaveResponse save(PostSaveRequest request) {
        elasticsearchOperations.save(ElasticPost.from(request));
        return new PostSaveResponse(true);
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getPost();
    }

    public SaveRequest fromPayload(Object object) {
        PostCreatedPayload payload = (PostCreatedPayload) object;
        PostSaveRequest request = new PostSaveRequest();
        request.setDomain(kafkaProperties.getAggregateType().getPost());
        request.setId(payload.getPostId());
        request.setBoardId(payload.getBoardId());
        request.setTitle(payload.getTitle());
        request.setContent(payload.getContent());
        return request;
    }
}