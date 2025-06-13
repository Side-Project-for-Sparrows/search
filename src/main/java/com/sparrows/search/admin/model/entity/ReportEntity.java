package com.sparrows.search.admin.model.entity;

import com.sparrows.search.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Builder
@Entity(name = "report")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Long reporter;
    Long reportee;

    String reason;
    OffsetDateTime reportTime;
}