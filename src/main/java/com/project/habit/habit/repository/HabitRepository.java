package com.project.habit.habit.repository;

import com.project.habit.habit.entity.Habit;
import com.project.habit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findByMember(Member member);

    Optional<Habit> findByIdAndMember(Long id, Member member);
}