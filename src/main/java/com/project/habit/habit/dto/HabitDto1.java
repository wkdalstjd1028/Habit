package com.project.habit.habit.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitDto1 {
    @Getter @Setter
    public class SignupRequest {
        private String username;
        private String password;
        private String nickname;
    }

    @Getter @Setter
    public class LoginRequest {
        private String username;
        private String password;
    }
}
