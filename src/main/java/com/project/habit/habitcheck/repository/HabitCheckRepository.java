package com.project.habit.habitcheck.repository;

import com.project.habit.habit.entity.Habit;
import com.project.habit.habitcheck.entity.HabitCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitCheckRepository extends JpaRepository<HabitCheck, Long> {

    // 오늘 체크 여부 확인용 (이미 쓰고 있을 거야)
    boolean existsByHabitAndCheckInDate(Habit habit, LocalDate checkInDate);

    // 월별 잔디 조회용으로 나중에 쓸 수 있음
    List<HabitCheck> findByHabitAndCheckInDateBetween(
            Habit habit,
            LocalDate start,
            LocalDate end
    );

    // ✅ 특정 습관에 대한 모든 체크인 기록 삭제
    void deleteByHabit(Habit habit);
}
