package com.sparrows.search.search.model.entity;

import com.sparrows.search.search.config.elasticsearch.ConsonantExtractor;
import com.sparrows.search.search.model.dto.SchoolSaveRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "schools")
public class ElasticSchool {
    @Id
    private String id;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "standard"),
            otherFields = { @InnerField(suffix = "keyword", type = FieldType.Keyword) })
    private String name;

    @Field(type = FieldType.Text, analyzer = "chosung_ngram_analyzer", searchAnalyzer = "standard")
    private String chosung;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getChosung() { return chosung; }

    public static ElasticSchool from(SchoolSaveRequest request){
        return ElasticSchool
                .builder()
                .id(request.getId()+"")
                .name(request.getName())
                .chosung(ConsonantExtractor.getConsonant(request.getName()))
                .build();
    }
}
