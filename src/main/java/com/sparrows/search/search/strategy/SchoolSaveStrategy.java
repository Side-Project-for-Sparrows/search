package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.payload.school.SchoolCreatedPayload;
import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.SaveRequest;
import com.sparrows.search.search.model.dto.school.SchoolSaveRequest;
import com.sparrows.search.search.model.dto.school.SchoolSaveResponse;
import com.sparrows.search.search.model.entity.ElasticSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

@Component
public class SchoolSaveStrategy implements SaveStrategy<SchoolSaveRequest, SchoolSaveResponse> {
    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    KafkaProperties kafkaProperties;

    @Override
    public SchoolSaveResponse save(SchoolSaveRequest request) {
        elasticsearchOperations.save(ElasticSchool.from(request));
        return new SchoolSaveResponse(true);
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getSchool();
    }

    public SaveRequest fromPayload(Object object) {
        SchoolCreatedPayload payload = (SchoolCreatedPayload) object;
        SchoolSaveRequest request = new SchoolSaveRequest();
        request.setDomain(kafkaProperties.getAggregateType().getSchool());
        request.setId(((Integer)payload.getSchoolId()).longValue());
        request.setName(payload.getSchoolName());
        return request;
    }
}