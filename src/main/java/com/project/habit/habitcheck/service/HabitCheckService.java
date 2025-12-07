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

    // 오늘 날짜로 체크인
    public void checkInToday(HabitCheckDTO habitCheckDTO) {

        // 1) habitId로 실제 Habit 엔티티 조회
        Habit habit = habitRepository.findById(habitCheckDTO.getHabitId())
                .orElseThrow(() -> new RuntimeException("해당 습관이 존재하지 않습니다."));

        LocalDate today = LocalDate.now();

        // 2) 오늘 이미 체크했는지 확인
        if (habitCheckRepository.existsByHabitAndCheckInDate(habit, today)) {
            throw new RuntimeException("오늘은 이미 체크인했습니다.");
        }

        // 3) 새로운 HabitCheck 저장
        HabitCheck checkIn = HabitCheck.builder()
                .habit(habit)
                .checkInDate(today)
                .build();

        habitCheckRepository.save(checkIn);
    }
}
