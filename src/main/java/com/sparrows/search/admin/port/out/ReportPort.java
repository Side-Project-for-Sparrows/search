package com.sparrows.search.admin.port.out;

import com.sparrows.search.admin.model.entity.ReportEntity;

import java.util.List;

public interface ReportPort {
    void save(ReportEntity report);

    ReportEntity findByReportId(Long id);

    List<ReportEntity> findByReporterId(Long id);
}
