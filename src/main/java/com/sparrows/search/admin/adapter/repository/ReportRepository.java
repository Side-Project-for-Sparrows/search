package com.sparrows.search.admin.adapter.repository;

import com.sparrows.search.admin.model.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    List<ReportEntity> findAllByReporter(long reporterId);
}
