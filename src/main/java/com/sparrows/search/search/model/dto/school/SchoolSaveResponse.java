package com.sparrows.search.search.model.dto.school;

import com.sparrows.search.search.model.dto.SaveResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSaveResponse implements SaveResponse {
    boolean success;
}
