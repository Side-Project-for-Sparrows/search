package com.sparrows.search.search.model.dto.board;

import com.sparrows.search.search.model.dto.SaveResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveResponse implements SaveResponse {
    boolean success;
}
