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


    private Long id;   // ✅ 추가

    @NotEmpty(message = "습관 이름은 필수 항목입니다.")
    private String name;

    @NotEmpty(message = "습관 타입은 필수 항목입니다.")
    private String habitType;

    private String description;

    // ✅ 엔티티 -> DTO 변환용 메서드 추가
    public static HabitDTO fromEntity(Habit habit) {
        if (habit == null) {
            return null;
        }

        return HabitDTO.builder()
                .id(habit.getId())
                .name(habit.getName())
                .habitType(habit.getHabitType().name()) // enum → 문자열
                .description(habit.getDescription())
                .build();
    }
}
