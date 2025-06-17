package com.sparrows.search.search.model.entity;

import com.sparrows.search.search.model.dto.log.LogSaveRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Document(indexName = "logs")
@Data
public class ElasticLog {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String traceId;

    @Field(type = FieldType.Keyword)
    private String spanId;

    @Field(type = FieldType.Keyword)
    private String parentSpanId;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String host;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String pod;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String container;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String clazz;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String method;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String level;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String message;

    public static ElasticLog from(LogSaveRequest req) {
        return ElasticLog.builder()
                .traceId(req.getTraceId())
                .spanId(req.getSpanId())
                .parentSpanId(req.getParentSpanId())
                .host(req.getHost())
                .pod(req.getPod())
                .container(req.getContainer())
                .method(req.getMethod())
                .clazz(req.getClazz())
                .level(req.getLevel())
                .message(req.getMessage())
                .build();
    }
}
