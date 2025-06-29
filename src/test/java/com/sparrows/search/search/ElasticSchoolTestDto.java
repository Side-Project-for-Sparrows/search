package com.sparrows.search.search;

import com.sparrows.search.kafka.payload.school.SchoolCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.adapter.in.SearchUsecaseImpl;
import com.sparrows.search.search.config.elasticsearch.ElasticConfig;
import com.sparrows.search.search.model.dto.SaveResponse;
import com.sparrows.search.search.model.dto.school.SchoolSearchRequest;
import com.sparrows.search.search.model.dto.school.SchoolSearchResponse;
import com.sparrows.search.search.model.entity.ElasticPost;
import com.sparrows.search.search.model.entity.ElasticSchool;
import com.sparrows.search.search.port.in.SearchUsecase;
import com.sparrows.search.search.strategy.SchoolSaveStrategy;
import com.sparrows.search.search.strategy.SchoolSearchStrategy;
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
        SchoolSaveStrategy.class,
        SchoolSearchStrategy.class,
        SearchUsecaseImpl.class,
        KafkaProperties.class
})
//@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
public class ElasticSchoolTestDto {
    @Autowired
    SearchUsecase searchUsecase;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    KafkaProperties kafkaProperties;

    @AfterEach
    void cleanup() {
        elasticsearchOperations.delete("-1", ElasticSchool.class);
        elasticsearchOperations.indexOps(ElasticPost.class).delete();
    }


    @Test
    void 학교_엘라스틱서치_삽입_및_검색() {
        // given
        SchoolCreatedPayload payload = new SchoolCreatedPayload();
        payload.setSchoolId(-1);
        payload.setSchoolName("예은고등학교");

        // when
        SaveResponse saveResponse = searchUsecase.save(kafkaProperties.getAggregateType().getSchool(), payload);
        elasticsearchOperations.indexOps(ElasticSchool.class).refresh(); // 이걸 꼭 추가

        // then
        assertTrue(saveResponse.isSuccess());

        // 검색
        SchoolSearchRequest searchRequest = new SchoolSearchRequest();
        searchRequest.setDomain(kafkaProperties.getAggregateType().getSchool());
        searchRequest.setQuery("예은");

        SchoolSearchResponse searchResponse =
                (SchoolSearchResponse) searchUsecase.search(searchRequest);

        //assertTrue(searchResponse.getSchoolDtos().contains(-1));

        SchoolSearchRequest searchRequest2 = new SchoolSearchRequest();
        searchRequest2.setDomain(kafkaProperties.getAggregateType().getSchool());
        searchRequest2.setQuery("병관");

        SchoolSearchResponse searchResponse2 =
                (SchoolSearchResponse) searchUsecase.search(searchRequest2);

        //assertTrue(searchResponse2.getIds().size() == 0);
    }
}

