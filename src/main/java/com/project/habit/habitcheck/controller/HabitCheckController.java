package com.project.habit.habitcheck.controller;

import com.project.habit.habitcheck.dto.HabitCheckDTO;
import com.project.habit.habitcheck.service.HabitCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitCheckController {

    private final HabitCheckService habitCheckService;

    @PostMapping("/checkin")
    public String checkin(@RequestBody HabitCheckDTO habitCheckDTO) {
        habitCheckService.checkInToday(habitCheckDTO);
        return "체크인 완료!";
    }
}