package com.sparrows.search.admin.model.dto;

import com.sparrows.search.admin.model.entity.FailureEntity;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class FailureRequestDto {
    String reason;
    String domain;
    OffsetDateTime reportTime;

    public static FailureEntity to(FailureRequestDto failureRequestDto){
        return FailureEntity.builder()
                .reason(failureRequestDto.getReason())
                .domain(failureRequestDto.getDomain())
                .reportTime(failureRequestDto.getReportTime())
                .build();
    }
}
