package com.project.habit.habit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitForm {

    @NotBlank
    private String title;

    private String description;

    private String color;
}
