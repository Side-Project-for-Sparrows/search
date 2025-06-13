package com.sparrows.search.admin.model.entity;

import com.sparrows.search.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Entity(name = "failure")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FailureEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String reason;
    String domain;
    OffsetDateTime reportTime;
}