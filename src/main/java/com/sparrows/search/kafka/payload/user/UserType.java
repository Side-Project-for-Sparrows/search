package com.sparrows.search.kafka.payload.user;

public enum UserType {
    ADMIN("admin"),
    OFFICIAL("official"),
    OUTSIDER("outsider");

    final String type;
    private UserType(String type){
        this.type = type;
    }

    public String toString(){
        return this.type;
    }
}
