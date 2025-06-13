package com.sparrows.search.search.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearchRequest implements SearchRequest {
    private String domain;
    private String query;

    public static BoardSearchRequest from(String query){
        return new BoardSearchRequest("board", query);
    }
}
