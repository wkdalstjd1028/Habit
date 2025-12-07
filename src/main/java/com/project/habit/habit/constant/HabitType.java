package com.project.habit.habit.constant;

public enum HabitType {
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY");

    private final String value;

    HabitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}