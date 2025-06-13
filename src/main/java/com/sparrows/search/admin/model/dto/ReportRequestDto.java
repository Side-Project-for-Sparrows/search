package com.sparrows.search.admin.model.dto;

import com.sparrows.search.admin.model.entity.ReportEntity;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ReportRequestDto {
    private Long id;

    Long reporter;
    Long reportee;

    String reason;
    OffsetDateTime reportTime;
    public static ReportEntity to(ReportRequestDto reportRequestDto){
        return ReportEntity.builder()
                .reporter(reportRequestDto.getReporter())
                .reportee(reportRequestDto.getReportee())
                .reason(reportRequestDto.getReason())
                .reportTime(reportRequestDto.getReportTime())
                .build();
    }
}
