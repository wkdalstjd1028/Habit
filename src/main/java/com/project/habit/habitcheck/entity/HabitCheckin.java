package com.project.habit.habitcheck.entity;

import com.project.habit.habit.entity.Habit;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "habit_checkins",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"habit_id", "checkinDate"})
        }
)
public class HabitCheckin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Habit habit;

    @Column(nullable = false)
    private LocalDate checkinDate;   // 체크한 날짜 (YYYY-MM-DD)

    @Column
    private String memo;            // 선택: 오늘 한 소감 한 줄
}