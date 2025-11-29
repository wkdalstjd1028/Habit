package com.project.habit.habit.controller;

import com.project.habit.habit.dto.HabitDto2;
import com.project.habit.habit.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/api/habits")
    public HabitDto2 createHabit(@AuthenticationPrincipal CustomUser user,
                                 @RequestBody HabitCreateRequest request) {
        Long userId = user.getId();
        return habitService.createHabit(userId, request);  // 👉 여기!
    }

    @GetMapping
    public String

}
