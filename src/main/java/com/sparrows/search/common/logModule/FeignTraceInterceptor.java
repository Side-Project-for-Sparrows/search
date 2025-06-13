package com.sparrows.search.common.logModule;

import com.sparrows.search.common.logModule.enums.TraceHeader;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class FeignTraceInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return;

        HttpServletRequest request = attrs.getRequest();

        String tid = Optional.ofNullable((String) request.getAttribute(TraceHeader.X_TRACE_ID.key())).orElse(UUID.randomUUID().toString());
        String pid = Optional.ofNullable((String) request.getAttribute(TraceHeader.X_PARENT_SPAN_ID.key())).orElseThrow();
        String cid = Optional.ofNullable((String) request.getAttribute(TraceHeader.X_SPAN_ID.key())).orElseThrow();

        template.header(TraceHeader.X_TRACE_ID.key(), tid);
        template.header(TraceHeader.X_PARENT_SPAN_ID.key(), cid);

        log.info("[FEIGN OUTBOUND] [URL={}] [method={}] [tid={}] [pid={}] [cid={}]",
                template.url(), template.method(), tid, pid, cid);
    }
}