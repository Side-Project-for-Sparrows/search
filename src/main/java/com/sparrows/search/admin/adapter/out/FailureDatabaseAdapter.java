package com.sparrows.search.admin.adapter.out;

import com.sparrows.search.admin.adapter.repository.FailureRepository;
import com.sparrows.search.admin.model.entity.FailureEntity;
import com.sparrows.search.admin.port.out.FailurePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FailureDatabaseAdapter implements FailurePort {

    @Autowired
    FailureRepository failureRepository;

    @Override
    public void save(FailureEntity report) {
        failureRepository.save(report);
    }

}
