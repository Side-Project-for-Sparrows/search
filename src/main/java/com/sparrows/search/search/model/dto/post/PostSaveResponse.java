package com.sparrows.search.search.model.dto.post;

import com.sparrows.search.search.model.dto.SaveResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveResponse implements SaveResponse {
    boolean success;
}
