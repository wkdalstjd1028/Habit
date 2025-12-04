package com.project.habit.habit.dto;

import com.project.habit.habit.entity.Habit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HabitViewDto {

    private final Long id;
    private final String title;
    private final String description;
    private final String color;
    private final boolean archived;

    public HabitViewDto(Habit habit) {
        this.id = habit.getId();
        this.title = habit.getTitle();
        this.description = habit.getDescription();
        this.color = habit.getColor();
        this.archived = habit.isArchived();
    }
}