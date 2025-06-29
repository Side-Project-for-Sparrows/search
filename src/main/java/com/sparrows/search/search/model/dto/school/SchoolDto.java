package com.sparrows.search.search.model.dto.school;

import com.sparrows.search.search.model.entity.ElasticSchool;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SchoolDto {
    Integer id;
    String name;

    public static SchoolDto from(ElasticSchool elasticSchool){
        return new SchoolDto(Integer.parseInt(elasticSchool.getId()),elasticSchool.getName());
    }
}
