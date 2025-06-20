package com.sparrows.search.search.model.dto.log;

import com.sparrows.search.kafka.payload.log.K8sInfo;
import com.sparrows.search.kafka.payload.log.LogCreatedPayload;
import com.sparrows.search.search.model.dto.SaveRequest;
import lombok.Data;

@Data
public class LogSaveRequest implements SaveRequest {
    private String traceId;
    private String spanId;
    private String parentSpanId;
    private String host;
    private String pod;
    private String container;
    private String clazz;
    private String method;
    private String level;
    private String message;
    private String domain;

    public static LogSaveRequest from(LogCreatedPayload payload) {
        LogSaveRequest req = new LogSaveRequest();
        req.traceId = payload.getTraceId();
        req.spanId = payload.getSpanId();
        req.parentSpanId = payload.getParentSpanId();
        req.clazz = payload.getClazz();
        req.method = payload.getMethod();
        req.level = payload.getLevel();
        req.message = payload.getMessage();

        if(payload.getK8sInfo() != null){
            req.host = payload.getK8sInfo().getHost();
            req.pod = payload.getK8sInfo().getPod();
            req.container = payload.getK8sInfo().getContainer();
        }
        return req;
    }

    @Override
    public String getDomain() {
        return this.domain;
    }
}
