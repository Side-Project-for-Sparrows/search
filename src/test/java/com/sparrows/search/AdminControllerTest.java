package com.sparrows.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.search.admin.model.dto.ReportRequestDto;
import com.sparrows.search.admin.model.entity.ReportEntity;
import com.sparrows.search.admin.port.out.ReportPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportPort reportPort;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void report_저장_정상동작() throws Exception {
        ReportRequestDto dto = new ReportRequestDto();
        dto.setReporter(10L);
        dto.setReportee(20L);
        dto.setReason("테스트 저장");

        mockMvc.perform(post("/admin/report")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/admin/report")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        List<ReportEntity> all = reportPort.findByReporterId(10L);
        assertEquals(all.size(),2);
        assertEquals("테스트 저장", all.get(0).getReason());
    }
}
