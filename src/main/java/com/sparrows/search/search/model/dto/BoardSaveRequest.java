package com.sparrows.search.search.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveRequest implements SaveRequest{
    Long id;
    String name;
    String description;
    String domain;

//    public static BoardSaveRequest from(BoardEntity entity){
//        return new BoardSaveRequest(((Integer)entity.getId()).longValue(), entity.getName(), entity.getDescription());
//    }

    @Override
    public String getDomain() {
        return this.domain;
    }
}
