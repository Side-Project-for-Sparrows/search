package com.sparrows.search.search.controller;

import com.sparrows.search.search.model.dto.BoardSearchRequest;
import com.sparrows.search.search.model.dto.PostSearchRequest;
import com.sparrows.search.search.model.dto.SchoolSearchRequest;
import com.sparrows.search.search.model.dto.SearchResponse;
import com.sparrows.search.search.port.in.SearchUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/index")
public class SearchController {
    @Autowired
    SearchUsecase searchUsecase;

    @PostMapping("/school")
    public SearchResponse search(@RequestBody SchoolSearchRequest request) {
        return searchUsecase.search(request);
    }

    @PostMapping("/board")
    public SearchResponse search(@RequestBody BoardSearchRequest request) {
        return searchUsecase.search(request);
    }

    @PostMapping("/post")
    public SearchResponse search(@RequestBody PostSearchRequest request) {
        return searchUsecase.search(request);
    }
}
