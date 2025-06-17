package com.sparrows.search.search.model.dto.post;

import com.sparrows.search.search.model.dto.SaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequest implements SaveRequest {
    Long id;
    Integer boardId;
    String title;
    String content;
    String domain;

    @Override
    public String getDomain() {
        return this.domain;
    }
}
