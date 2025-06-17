package com.sparrows.search.search;

import com.sparrows.search.kafka.payload.board.BoardCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.adapter.in.SearchUsecaseImpl;
import com.sparrows.search.search.config.elasticsearch.ElasticConfig;
import com.sparrows.search.search.model.dto.board.BoardSearchRequest;
import com.sparrows.search.search.model.dto.board.BoardSearchResponse;
import com.sparrows.search.search.model.dto.SaveResponse;
import com.sparrows.search.search.model.entity.ElasticBoard;
import com.sparrows.search.search.port.in.SearchUsecase;
import com.sparrows.search.search.strategy.BoardSaveStrategy;
import com.sparrows.search.search.strategy.BoardSearchStrategy;
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
        BoardSaveStrategy.class,
        BoardSearchStrategy.class,
        SearchUsecaseImpl.class,
        KafkaProperties.class
})
//@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
public class ElasticBoardTest {
    @Autowired
    SearchUsecase searchUsecase;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    KafkaProperties kafkaProperties;

    @AfterEach
    void cleanup() {
        elasticsearchOperations.delete("-1", ElasticBoard.class);
        // 혹은 인덱스 자체 삭제
        // elasticsearchOperations.indexOps(ElasticPost.class).delete();
    }


    @Test
    void 게시판_엘라스틱서치_삽입_및_검색() {
        // given
        BoardCreatedPayload payload = new BoardCreatedPayload();
        payload.setBoardId(-1);
        payload.setName("병관 고등학교 게시판");
        payload.setDescription("지렁이들이 참 많습니다");

        // when
        SaveResponse saveResponse = searchUsecase.save(kafkaProperties.getAggregateType().getBoard(), payload);
        elasticsearchOperations.indexOps(ElasticBoard.class).refresh();

        // then
        assertTrue(saveResponse.isSuccess());

        // 검색
        BoardSearchRequest searchRequest = new BoardSearchRequest();
        searchRequest.setDomain(kafkaProperties.getAggregateType().getBoard());
        searchRequest.setQuery("병관");

        BoardSearchResponse searchResponse =
                (BoardSearchResponse) searchUsecase.search(searchRequest);

        assertTrue(searchResponse.getIds().contains(-1L));

        BoardSearchRequest searchRequest2 = new BoardSearchRequest();
        searchRequest2.setDomain(kafkaProperties.getAggregateType().getBoard());
        searchRequest2.setQuery("나띵");

        BoardSearchResponse searchResponse2 =
                (BoardSearchResponse) searchUsecase.search(searchRequest2);

        assertTrue(!searchResponse2.getIds().contains(-1L));
    }
}

