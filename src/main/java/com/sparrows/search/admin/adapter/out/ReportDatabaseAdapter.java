package com.sparrows.search.admin.adapter.out;

import com.sparrows.search.admin.adapter.repository.ReportRepository;
import com.sparrows.search.admin.model.entity.ReportEntity;
import com.sparrows.search.admin.port.out.ReportPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportDatabaseAdapter implements ReportPort {

    @Autowired
    ReportRepository reportRepository;

    @Override
    public void save(ReportEntity report) {
        reportRepository.save(report);
    }

    @Override
    public ReportEntity findByReportId(Long id) {
        return reportRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<ReportEntity> findByReporterId(Long id) {
        return reportRepository.findAllByReporter(id);
    }
}
