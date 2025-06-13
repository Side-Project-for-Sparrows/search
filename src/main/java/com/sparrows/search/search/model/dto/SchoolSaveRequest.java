package com.sparrows.search.search.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSaveRequest implements SaveRequest{
    Long id;
    String name;
    String domain;

//    public static SchoolSaveRequest from(SchoolEntity entity){
//        return new SchoolSaveRequest(((Integer)(entity.getId())).longValue(), entity.getName());
//    }

    @Override
    public String getDomain() {
        return this.domain;
    }
}
