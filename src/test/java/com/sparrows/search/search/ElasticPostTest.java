package com.sparrows.search.search;

import com.sparrows.search.kafka.payload.board.PostCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.adapter.in.SearchUsecaseImpl;
import com.sparrows.search.search.config.elasticsearch.ElasticConfig;
import com.sparrows.search.search.model.dto.PostSearchRequest;
import com.sparrows.search.search.model.dto.PostSearchResponse;
import com.sparrows.search.search.model.dto.SaveResponse;
import com.sparrows.search.search.model.entity.ElasticPost;
import com.sparrows.search.search.port.in.SearchUsecase;
import com.sparrows.search.search.strategy.PostSaveStrategy;
import com.sparrows.search.search.strategy.PostSearchStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        ElasticConfig.class,
        PostSaveStrategy.class,
        PostSearchStrategy.class,
        SearchUsecaseImpl.class,
        KafkaProperties.class
})
//@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
public class ElasticPostTest {
    @Autowired
    SearchUsecase searchUsecase;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    KafkaProperties kafkaProperties;

    @AfterEach
    void cleanup() {
        elasticsearchOperations.delete("-1", ElasticPost.class);
        // 혹은 인덱스 자체 삭제
        // elasticsearchOperations.indexOps(ElasticPost.class).delete();
    }


    @Test
    void 게시글_엘라스틱서치_삽입_및_검색_with_boardId() {
        // given
        PostCreatedPayload payload = new PostCreatedPayload();
        payload.setPostId(-1L);
        payload.setTitle("학교 축제 후기");
        payload.setContent("정말 즐거운 경험이었습니다. 음식도 맛있었고!");
        payload.setBoardId(101); // 💡 boardId 설정 필요

        SaveResponse saveResponse = searchUsecase.save(kafkaProperties.getAggregateType().getPost(), payload);
        elasticsearchOperations.indexOps(ElasticPost.class).refresh();

        assertTrue(saveResponse.isSuccess());

        // when: boardId가 일치하는 검색
        PostSearchRequest matched = new PostSearchRequest();
        matched.setDomain(kafkaProperties.getAggregateType().getPost());
        matched.setQuery("축제");
        matched.setBoardId(101); // 💡 정확히 일치하는 boardId

        PostSearchResponse response1 = (PostSearchResponse) searchUsecase.search(matched);
        assertTrue(response1.getIds().contains(-1L));

        // when: boardId가 다른 경우 검색 안됨
        PostSearchRequest mismatched = new PostSearchRequest();
        mismatched.setDomain(kafkaProperties.getAggregateType().getPost());
        mismatched.setQuery("축제");
        mismatched.setBoardId(999); // ❌ 존재하지 않는 boardId

        PostSearchResponse response2 = (PostSearchResponse) searchUsecase.search(mismatched);
        assertTrue(response2.getIds().isEmpty());
    }

}

