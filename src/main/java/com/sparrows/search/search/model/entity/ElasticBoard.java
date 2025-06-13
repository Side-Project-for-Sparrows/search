package com.sparrows.search.search.model.entity;

import com.sparrows.search.search.model.dto.BoardSaveRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "boards")
public class ElasticBoard {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    public static ElasticBoard from(BoardSaveRequest request){
        return ElasticBoard
                .builder()
                .id(request.getId()+"")
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
