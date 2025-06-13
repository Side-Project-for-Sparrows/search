package com.sparrows.search.admin.port.out;

import com.sparrows.search.admin.model.entity.FailureEntity;

public interface FailurePort {
    void save(FailureEntity report);
}
