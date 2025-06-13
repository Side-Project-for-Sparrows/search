package com.sparrows.search.admin.port.in;

import com.sparrows.search.admin.model.entity.FailureEntity;
import com.sparrows.search.admin.model.entity.ReportEntity;

public interface AdminUseCase {
    void submitReport(ReportEntity report);

    void submitFailure(FailureEntity failure);
}
