package com.project.habit.habitcheck.repository;

import com.project.habit.habit.entity.Habit;
import com.project.habit.habitcheck.entity.HabitCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HabitCheckRepository extends JpaRepository<HabitCheck, Long> {
    boolean existsByHabitAndCheckInDate(Habit habit, LocalDate date);
}
