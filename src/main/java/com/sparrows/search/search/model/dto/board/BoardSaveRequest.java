package com.sparrows.search.search.model.dto.board;

import com.sparrows.search.search.model.dto.SaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveRequest implements SaveRequest {
    Long id;
    String name;
    String description;
    String domain;

    @Override
    public String getDomain() {
        return this.domain;
    }
}
