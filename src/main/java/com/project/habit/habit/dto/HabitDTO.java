package com.project.habit.habit.dto;

import com.project.habit.habit.entity.Habit;   // ✅ 이 줄 추가
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitDTO {


    private Long id;

    @NotEmpty(message = "습관 이름은 필수 항목입니다.")
    private String name;

    @NotEmpty(message = "습관 타입은 필수 항목입니다.")
    private String habitType;

    private String description;

    public static HabitDTO fromEntity(Habit habit) {
        if (habit == null) {
            return null;
        }

        return HabitDTO.builder()
                .id(habit.getId())
                .name(habit.getName())
                .habitType(habit.getHabitType().name())
                .description(habit.getDescription())
                .build();
    }
}
