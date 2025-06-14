package com.sparrows.search.common.logModule;

import com.sparrows.search.common.logModule.enums.TraceHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ControllerLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            String tid = Optional.ofNullable(request.getHeader(TraceHeader.X_TRACE_ID.key())).orElse(UUID.randomUUID().toString());
            String pid = Optional.ofNullable(request.getHeader(TraceHeader.X_PARENT_SPAN_ID.key())).orElse(TraceHeader.ROOT.key());
            String cid = UUID.randomUUID().toString(); // 현재 서비스 기준 새로운 CID
            String method = handlerMethod.getMethod().getName();

            request.setAttribute(TraceHeader.X_TRACE_ID.key(), tid);
            request.setAttribute(TraceHeader.X_PARENT_SPAN_ID.key(), pid);
            request.setAttribute(TraceHeader.X_SPAN_ID.key(), cid);

            MDC.put(TraceHeader.X_TRACE_ID.key(), tid);
            MDC.put(TraceHeader.X_PARENT_SPAN_ID.key(), pid);
            MDC.put(TraceHeader.X_SPAN_ID.key(), cid);
            MDC.put("METHOD",method);
            MDC.put("flow", "IN");

            return true;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod handlerMethod) {
            String tid = (String) request.getAttribute(TraceHeader.X_TRACE_ID.key());
            String pid = (String) request.getAttribute(TraceHeader.X_PARENT_SPAN_ID.key());
            String cid = (String) request.getAttribute(TraceHeader.X_SPAN_ID.key());

            MDC.put(TraceHeader.X_TRACE_ID.key(),tid);
            MDC.put(TraceHeader.X_PARENT_SPAN_ID.key(),pid);
            MDC.put(TraceHeader.X_SPAN_ID.key(),cid);
            MDC.put("METHOD",handlerMethod.getMethod().getName());
            MDC.put("flow","OUT");
        }
    }
}