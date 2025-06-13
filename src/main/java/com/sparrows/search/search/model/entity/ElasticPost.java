package com.sparrows.search.search.model.entity;

import com.sparrows.search.search.model.dto.PostSaveRequest;
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
@Document(indexName = "posts")
public class ElasticPost {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String boardId;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String content;

    public static ElasticPost from(PostSaveRequest request) {
        return ElasticPost.builder()
                .id(String.valueOf(request.getId()))
                .boardId(String.valueOf(request.getBoardId()))
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
