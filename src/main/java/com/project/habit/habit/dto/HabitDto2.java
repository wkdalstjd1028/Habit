package com.project.habit.habit.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitDto2 {

    @Getter @Setter
    public static class CreateRequest {
        private String title;
        private String description;
        private LocalDate startDate;
    }

    @Getter @Setter
    public static class UpdateRequest {
        private String title;
        private String description;
        private Boolean active;
        private LocalDate endDate;
    }

    @Getter @Setter
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean active;
    }
}
