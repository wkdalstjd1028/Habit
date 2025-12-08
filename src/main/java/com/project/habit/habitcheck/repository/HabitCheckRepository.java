package com.project.habit.habitcheck.repository;

import com.project.habit.habit.entity.Habit;
import com.project.habit.habitcheck.entity.HabitCheck;
import com.project.habit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitCheckRepository extends JpaRepository<HabitCheck, Long> {

    boolean existsByHabitAndCheckInDate(Habit habit, LocalDate checkInDate);

    List<HabitCheck> findByHabitAndCheckInDateBetween(
            Habit habit,
            LocalDate start,
            LocalDate end
    );

    void deleteByHabit(Habit habit);

    // ✅ 오늘 이 회원이 체크한 습관들
    List<HabitCheck> findByHabit_MemberAndCheckInDate(Member member, LocalDate checkInDate);
}
