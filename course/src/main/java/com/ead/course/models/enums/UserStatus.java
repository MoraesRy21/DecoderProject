package com.ead.course.models.enums;

public enum UserStatus {
    ACTIVE("Active", 1),
    DEACTIVATE("Deactivate", -1),
    BLOCKED ("Blocked", 0);

    private String name;
    private Integer numberStatus;

    UserStatus(String name, Integer numberStatus) {
         this.name = name;
         this.numberStatus = numberStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberStatus() {
        return numberStatus;
    }

    public void setNumberStatus(Integer numberStatus) {
        this.numberStatus = numberStatus;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
