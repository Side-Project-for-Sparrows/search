package com.sparrows.search.common.logModule.enums;

public enum TraceHeader {
    X_TRACE_ID("X_TRACE_ID"),
    X_PARENT_SPAN_ID("X_PARENT_SPAN_ID"),
    X_SPAN_ID("X_SPAN_ID"),
    ROOT("ROOT");

    private final String key;

    TraceHeader(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}