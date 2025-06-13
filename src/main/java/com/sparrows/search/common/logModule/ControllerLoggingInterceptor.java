package com.sparrows.search.common.logModule;

import com.sparrows.search.common.logModule.enums.TraceHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
        String tid = Optional.ofNullable(request.getHeader(TraceHeader.X_TRACE_ID.key())).orElse(UUID.randomUUID().toString());
        String pid = Optional.ofNullable(request.getHeader(TraceHeader.X_PARENT_SPAN_ID.key())).orElse(TraceHeader.ROOT.key());
        String cid = UUID.randomUUID().toString(); // 현재 서비스 기준 새로운 CID

        request.setAttribute(TraceHeader.X_TRACE_ID.key(), tid);
        request.setAttribute(TraceHeader.X_PARENT_SPAN_ID.key(), pid);
        request.setAttribute(TraceHeader.X_SPAN_ID.key(), cid);

        if (handler instanceof HandlerMethod handlerMethod) {
            log.info("[INBOUND] [{}] [tid={}] [pid={}] [cid={}] 요청 수신",
                    handlerMethod.getMethod().getDeclaringClass().getSimpleName() + "." +
                            handlerMethod.getMethod().getName(), tid, pid, cid);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod handlerMethod) {
            log.info("[OUTBOUND] [{}] [tid={}] [pid={}] [cid={}] 응답 완료",
                    handlerMethod.getMethod().getDeclaringClass().getSimpleName() + "." +
                            handlerMethod.getMethod().getName(),
                    request.getAttribute(TraceHeader.X_TRACE_ID.key()),
                    request.getAttribute(TraceHeader.X_PARENT_SPAN_ID.key()),
                    request.getAttribute(TraceHeader.X_SPAN_ID.key()));
        }
    }
}