package com.sparrows.search.admin.adapter.in;

import com.sparrows.search.admin.model.entity.FailureEntity;
import com.sparrows.search.admin.model.entity.ReportEntity;
import com.sparrows.search.admin.port.in.AdminUseCase;
import com.sparrows.search.admin.port.out.FailurePort;
import com.sparrows.search.admin.port.out.ReportPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUseCaseImpl implements AdminUseCase {
    @Autowired
    ReportPort reportPort;

    @Autowired
    FailurePort failurePort;

    @Override
    public void submitReport(ReportEntity report) {
        reportPort.save(report);
    }

    @Override
    public void submitFailure(FailureEntity failure) {
        failurePort.save(failure);
    }

}
