package com.ead.authuser.model.enums;

public enum CourseStatus {
    NOT_STATERTED("Not Started", 0),
    IN_PROGRESS("In Progress", 1),
    CONCLUDED("Concluded", 2);

    String name;
    Integer statusNum;

    CourseStatus(String name, Integer statusNum) {
        this.name = name;
        this.statusNum = statusNum;
    }

    @Override
    public String toString() {
        return name;
    }
}
