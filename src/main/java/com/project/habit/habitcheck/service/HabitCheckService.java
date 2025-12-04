package com.project.habit.habitcheck.service;

import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.habitcheck.dto.HabitCheckDTO;
import com.project.habit.habitcheck.entity.HabitCheck;
import com.project.habit.habitcheck.repository.HabitCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HabitCheckService {

    private final HabitRepository habitRepository;
    private final HabitCheckRepository habitCheckRepository;

    public void checkInToday(HabitCheckDTO habitCheckDTO) {

        Habit habit = habitCheckRepository.findById(habitCheckDTO.getHabitId())
                .orElseThrow(() -> new RuntimeException("해당 습관이 존재하지 않습니다.")).getHabit();

        LocalDate today = LocalDate.now();

        // 중복 체크
        if (habitCheckRepository.existsByHabitAndCheckInDate(habit, today)) {
            throw new RuntimeException("오늘은 이미 체크인했습니다.");
        }

        HabitCheck checkIn = HabitCheck.builder()
                .habit(habit)
                .checkInDate(today)
                .build();

        habitCheckRepository.save(checkIn);
    }
}
