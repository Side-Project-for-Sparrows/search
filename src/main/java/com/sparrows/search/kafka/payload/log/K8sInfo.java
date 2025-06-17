package com.sparrows.search.kafka.payload.log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class K8sInfo{
    @JsonProperty("host")
    private String host;

    @JsonProperty("pod_name")
    private String pod;

    @JsonProperty("container_name")
    private String container;
}