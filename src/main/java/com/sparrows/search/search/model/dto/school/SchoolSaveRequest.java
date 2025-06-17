package com.sparrows.search.search.model.dto.school;

import com.sparrows.search.search.model.dto.SaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSaveRequest implements SaveRequest {
    Long id;
    String name;
    String domain;

    @Override
    public String getDomain() {
        return this.domain;
    }
}
