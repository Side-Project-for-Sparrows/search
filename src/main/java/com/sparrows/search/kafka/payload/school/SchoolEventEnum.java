package com.sparrows.search.kafka.payload.school;

public enum SchoolEventEnum {
    SchoolCreated("SchoolCreated"),
    SchoolUpdated("SchoolUpdated"),
    SchoolDeleted("SchoolDeleted");

    String event;

    SchoolEventEnum(String event){
        this.event = event;
    }
}
