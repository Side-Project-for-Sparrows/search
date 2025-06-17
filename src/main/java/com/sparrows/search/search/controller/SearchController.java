package com.sparrows.search.search.controller;

import com.sparrows.search.common.exception.handling.AccessDeniedException;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.*;
import com.sparrows.search.search.model.dto.board.BoardSearchRequest;
import com.sparrows.search.search.model.dto.log.LogSearchRequest;
import com.sparrows.search.search.model.dto.post.PostSearchRequest;
import com.sparrows.search.search.model.dto.school.SchoolSearchRequest;
import com.sparrows.search.search.port.in.SearchUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/index")
public class SearchController {
    private final SearchUsecase searchUsecase;
    private final KafkaProperties kafkaProperties;

    @PostMapping("/school")
    public SearchResponse search(@RequestBody SchoolSearchRequest request) {
        validate(request.getDomain(), kafkaProperties.getAggregateType().getSchool());

        return searchUsecase.search(request);
    }

    @PostMapping("/board")
    public SearchResponse search(@RequestBody BoardSearchRequest request) {
        validate(request.getDomain(), kafkaProperties.getAggregateType().getBoard());

        return searchUsecase.search(request);
    }

    @PostMapping("/post")
    public SearchResponse search(@RequestBody PostSearchRequest request) {
        validate(request.getDomain(), kafkaProperties.getAggregateType().getPost());

        return searchUsecase.search(request);
    }

    @PostMapping("/log")
    public SearchResponse search(@RequestBody LogSearchRequest request) {
        validate(request.getDomain(), kafkaProperties.getAggregateType().getLog());

        return searchUsecase.search(request);
    }

    private void validate(String requestAggregateType, String aggregateType) {
        if (!aggregateType.equals(requestAggregateType)) {
            throw new AccessDeniedException();
        }
    }
}
