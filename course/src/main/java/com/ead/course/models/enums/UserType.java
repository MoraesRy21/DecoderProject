package com.ead.course.models.enums;

public enum UserType {
    ADMIM("Administrator"),
    STUDENT("Student"),
    INSTRUCTOR("Instructor");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
