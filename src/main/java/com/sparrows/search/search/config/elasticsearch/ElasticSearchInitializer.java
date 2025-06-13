package com.sparrows.search.search.config.elasticsearch;

import com.sparrows.search.search.model.entity.ElasticBoard;
import com.sparrows.search.search.model.entity.ElasticPost;
import com.sparrows.search.search.model.entity.ElasticSchool;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class ElasticSearchInitializer {
    private final ElasticsearchOperations elasticsearchOperations;
    @PostConstruct
    public void init(){
        initBoard();
        initPost();
        initSchool();
    }

    private void initBoard() {
        IndexOperations indexOperations = elasticsearchOperations.indexOps(ElasticBoard.class);
        if (!indexOperations.exists()) {
            indexOperations.create();                       // 인덱스 생성
            indexOperations.putMapping(indexOperations.createMapping());  // 매핑 적용
        }
    }

    private void initPost() {
        IndexOperations indexOperations = elasticsearchOperations.indexOps(ElasticPost.class);

        if (!indexOperations.exists()) {
            indexOperations.create();                       // 인덱스 생성
            indexOperations.putMapping(indexOperations.createMapping());  // 매핑 적용
        }
    }

    private void initSchool(){
        IndexOperations indexOps = elasticsearchOperations.indexOps(ElasticSchool.class);
        if(indexOps.exists()) {
            log.error("IS EXIST");
            return;
        };

        Map<String, Object> settings = Map.of(
                "index", Map.of("max_ngram_diff", 10),
                "analysis", Map.of(
                        "tokenizer", Map.of(
                                "chosung_ngram_tokenizer", Map.of(
                                        "type", "ngram",
                                        "min_gram", 1,
                                        "max_gram", 10,
                                        "token_chars", List.of("letter", "digit")
                                )
                        ),
                        "analyzer", Map.of(
                                "chosung_ngram_analyzer", Map.of(
                                        "tokenizer", "chosung_ngram_tokenizer"
                                )
                        )
                )
        );

        indexOps.create(settings);       // 인덱스 생성 + 설정 반영
        indexOps.putMapping(indexOps.createMapping());
    }
}
