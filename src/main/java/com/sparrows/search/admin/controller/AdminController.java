package com.sparrows.search.admin.controller;

import com.sparrows.search.admin.model.dto.FailureRequestDto;
import com.sparrows.search.admin.model.dto.ReportRequestDto;
import com.sparrows.search.admin.port.in.AdminUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
@Slf4j
public class AdminController {
    @Autowired
    AdminUseCase adminUseCase;

    @Value("${timezone.offset}")
    int offset;

    @PostMapping("/admin/report")
    public void saveReport(@RequestBody ReportRequestDto dto) {
        log.info("📥 Report 수신: {}", dto.getReason());
        OffsetDateTime time = dto.getReportTime() != null
                ? dto.getReportTime()
                : OffsetDateTime.now(ZoneOffset.ofHours(offset)); // 대한민국 시간 기준
        dto.setReportTime(time);

        adminUseCase.submitReport(ReportRequestDto.to(dto));
        log.info("✅ Report 저장 완료");
    }

    @PostMapping("/admin/failure")
    public void saveFailure(@RequestBody FailureRequestDto dto) {
        log.info("📥 failure 수신: {}", dto.getReason());
        OffsetDateTime time = dto.getReportTime() != null
                ? dto.getReportTime()
                : OffsetDateTime.now(ZoneOffset.ofHours(offset));
        dto.setReportTime(time);

        adminUseCase.submitFailure(FailureRequestDto.to(dto));
        log.info("✅ Failure 저장 완료");
    }
}
