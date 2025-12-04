package com.project.habit.habit.repository;

import com.project.habit.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    // 로그인한 회원의 습관 목록
    List<Habit> findByMemberIdAndArchivedFalse(Long memberId);

    // 내가 가진 습관 1개
    Optional<Habit> findByIdAndMemberId(Long habitId, Long memberId);
}
