package com.project.habit.habit.repository;

import com.project.habit.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Optional<Habit> findByUserIdAndActiveTrue(Long userId);
}
