package com.project.habit.habit.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitDTO {

    @NotEmpty(message = "습관 이름은 필수 항목입니다.")
    private String name;

    @NotEmpty(message = "습관 타입은 필수 항목입니다.")
    private String habitType;

    private String description;
}