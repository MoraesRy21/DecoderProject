package com.ead.authuser.model.enums;

public enum CourseLevel {
    BEGINNER("Beginner", 1),
    INTERMEDIARY("Intermediary", 2),
    ADVANCED("Advanced", 3);

    String name;
    Integer levelNum;

    CourseLevel(String name, Integer levelNum) {
        this.name = name;
        this.levelNum = levelNum;
    }

    @Override
    public String toString() {
        return name;
    }
}
