package com.project.habit.habit.controller;

import com.project.habit.habit.dto.HabitDto2;
import com.project.habit.habit.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/api/habits")
    public HabitDto2 createHabit(@AuthenticationPrincipal CustomUser user,
                                 @RequestBody HabitDto2 habitDto2) {

        Long userId = user.getId();
        return habitService.createHabit(userId, habitDto2);
    }

    @GetMapping("/habits")
    public String habitsPage() {
        return "habit/list";
    }
}
