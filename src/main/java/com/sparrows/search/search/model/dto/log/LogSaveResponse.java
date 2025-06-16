package com.sparrows.search.search.model.dto.log;

import com.sparrows.search.search.model.dto.SaveResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogSaveResponse implements SaveResponse {
    private boolean success;
}